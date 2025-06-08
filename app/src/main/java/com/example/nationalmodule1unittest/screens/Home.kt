package com.example.nationalmodule1unittest.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
    val scope = rememberCoroutineScope()

    LazyColumn(contentPadding = innerPadding, modifier = Modifier.padding(horizontal = 10.dp)) {
        stickyHeader {
            val choiceButtons = listOf("所有", "學習中")
            var selected by remember { mutableStateOf(choiceButtons[0]) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("單字列表", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { navController.navigate(Screens.NewCard.name) }) {
                    Icon(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = "add"
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
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
        }
        items(cards) {
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
                        contentDescription = null
                    )
                }
            }
            HorizontalDivider()
        }
    }
}