package com.example.nationalmodule1unittest.screens

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nationalmodule1unittest.CardDao

@Composable
fun CardScreen(innerPadding: PaddingValues, card: CardDao, navController: NavHostController) {
    val cards by card.getAllCards().collectAsState(emptyList())
    val pagerState = rememberPagerState { cards.size }
    var showChinese by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .padding(innerPadding),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp), horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Settings, contentDescription = "")
            }
        }
        HorizontalPager(pagerState, modifier = Modifier.align(Alignment.Center)) {
            val current = cards[it]
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp, vertical = 100.dp), onClick = {
                    showChinese = !showChinese
                }, elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(modifier = Modifier.align(Alignment.TopCenter)) {
                        if (current.learning) {
                            Spacer(Modifier.height(20.dp))
                            Text(
                                "學習中",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onSecondary)
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (showChinese) {
                            Text("中文")
                            Text(current.tw, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Text("英文")
                            Text(current.en, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
