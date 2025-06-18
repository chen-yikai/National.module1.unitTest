package com.example.nationalmodule1unittest.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
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
    val rotateDeg = animateFloatAsState(
        targetValue = if (showChinese) 180f else 0f,
        animationSpec = tween(durationMillis = 300), label = "rotate deg"
    )

    Box(
        modifier = Modifier
            .testTag("card_screen")
            .padding(innerPadding),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        ) {
            Text(
                "${pagerState.currentPage + 1}/${cards.size}",
                modifier = Modifier.testTag("card_current_position")
            )
        }

        HorizontalPager(
            pagerState,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .testTag("horizontal_pager_tag")
        ) {
            val current = cards[it]
            LaunchedEffect(it) {
                showChinese = true
            }

            Box(modifier = Modifier.testTag("pager_card_$it")) {
                Card(
                    modifier = Modifier
                        .testTag(if (showChinese) "chinese_card" else "english_card")
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = rotateDeg.value
                            cameraDistance = 6 * density
                        }
                        .graphicsLayer {
                            if (showChinese) rotationY = 180f
                        }
                        .padding(horizontal = 40.dp, vertical = 100.dp),
                    onClick = { showChinese = !showChinese },
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
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
                            Text(
                                if (showChinese) current.tw else current.en,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.testTag(if (showChinese) "tw_text" else "en_text")
                            )
                        }
                    }
                }
            }
        }
    }
}
