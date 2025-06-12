package com.example.nationalmodule1unittest.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nationalmodule1unittest.Card
import com.example.nationalmodule1unittest.CardDao
import com.example.nationalmodule1unittest.R
import kotlinx.coroutines.launch

@Composable
fun EditCardScreen(
    innerPadding: PaddingValues,
    card: CardDao,
    navController: NavHostController,
    id: Int?
) {
    var en by remember { mutableStateOf("") }
    var tw by remember { mutableStateOf("") }

    val cardDetail by card.getCard(id ?: 0).collectAsState(null)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 5.dp)
    ) {
        Row {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(painter = painterResource(R.drawable.back), contentDescription = "back")
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Text("編輯單字", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(20.dp))
            TextField(
                value = en,
                onValueChange = { en = it },
                placeholder = { Text("英文單字 (${cardDetail?.en})") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            TextField(
                value = tw,
                onValueChange = { tw = it },
                placeholder = { Text("中文意思 (${cardDetail?.tw})") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                scope.launch {
                    if (id != null) {
                        card.updateDetail(id, en, tw)
                        navController.popBackStack()
                    }
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("編輯")
            }
        }
    }
}