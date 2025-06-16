package com.example.nationalmodule1unittest

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.nationalmodule1unittest.screens.CardScreen
import com.example.nationalmodule1unittest.screens.EditCardScreen
import com.example.nationalmodule1unittest.screens.HomeScreen
import com.example.nationalmodule1unittest.screens.NewCardScreen

@Composable
fun Nav(context: Context, navController: NavHostController) {
    val currentScreen by navController.currentBackStackEntryFlow.collectAsState(null)
    val navItems =
        mapOf(Screens.單字卡.name to R.drawable.home, Screens.輪轉單字卡.name to R.drawable.mark)

    val room: CardDatabase = Room.databaseBuilder(context, CardDatabase::class.java, "app_db")
        .fallbackToDestructiveMigration().build()
    val card: CardDao = room.cardDao()

    Scaffold(bottomBar = {
        if (currentScreen?.destination?.route == Screens.單字卡.name || currentScreen?.destination?.route == Screens.輪轉單字卡.name) {
            NavigationBar {
                navItems.forEach {
                    NavigationBarItem(
                        onClick = { navController.navigate(it.key) },
                        label = { Text(it.key) },
                        icon = {
                            Icon(
                                painter = painterResource(it.value),
                                contentDescription = it.key
                            )
                        },
                        modifier = Modifier.testTag("nav_item_${it.key}"),
                        selected = currentScreen?.destination?.route == it.key
                    )
                }
            }
        }
    }) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screens.單字卡.name,
            enterTransition = { EnterTransition.None }, exitTransition = { ExitTransition.None }) {
            composable(Screens.單字卡.name) { HomeScreen(innerPadding, card, navController) }
            composable(Screens.輪轉單字卡.name) { CardScreen(innerPadding, card, navController) }
            composable(
                Screens.NewCard.name,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { it } }) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    NewCardScreen(
                        innerPadding,
                        card,
                        navController
                    )
                }
            }
            composable(
                "${Screens.EditCard.name}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
                enterTransition = { slideInVertically { it } },
                exitTransition = { slideOutVertically { it } }
            ) { entry ->
                val id = entry.arguments?.getInt("id") ?: 0
                Surface(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ) {
                    EditCardScreen(
                        innerPadding,
                        card,
                        navController,
                        id
                    )
                }
            }
        }
    }
}

enum class Screens {
    單字卡, 輪轉單字卡, NewCard, EditCard, ReviewSettings
}