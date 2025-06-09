package com.example.nationalmodule1unittest.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nationalmodule1unittest.CardDao
import com.example.nationalmodule1unittest.R
import com.example.nationalmodule1unittest.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(innerPadding: PaddingValues, card: CardDao, navController: NavHostController) {
    val cards by card.getAllCards().collectAsState(emptyList())
    val cardsLearning by card.getLearningCards().collectAsState(emptyList())
    val scope = rememberCoroutineScope()
    val choiceButtons = listOf("所有", "學習中")
    var selected by remember { mutableStateOf(choiceButtons[0]) }

    LazyColumn(contentPadding = innerPadding, modifier = Modifier.padding(horizontal = 10.dp)) {
        stickyHeader {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("單字列表", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { navController.navigate(Screens.NewCard.name) }) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        contentDescription = "add"
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                choiceButtons.forEachIndexed { index, item ->
                    SegmentedButton(
                        selected = item == selected,
                        onClick = { selected = item },
                        label = { Text(item) }, shape = SegmentedButtonDefaults.itemShape(
                            index = index, count = choiceButtons.size
                        )
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
        }
        items(if (selected == "所有") cards else cardsLearning) {
            Card(
                modifier = Modifier.padding(vertical = 5.dp),
                onClick = { navController.navigate("${Screens.EditCard.name}/${it.id}") }) {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.speak),
                            contentDescription = "speak"
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(it.en, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(it.tw, color = Color.Gray)
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = {
                        scope.launch {
                            card.updateLearning(it.id, !it.learning)
                        }
                    }) {
                        Icon(
                            painter = painterResource(if (it.learning) R.drawable.is_learning else R.drawable.not_learning),
                            contentDescription = null,
                            tint = if (it.learning) Color(0xffFF9F00).copy(alpha = 0.7f) else Color.Gray
                        )
                    }
                }
            }
        }
    }
}