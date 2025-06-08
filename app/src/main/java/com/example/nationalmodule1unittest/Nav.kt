package com.example.nationalmodule1unittest

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.nationalmodule1unittest.screens.CardScreen
import com.example.nationalmodule1unittest.screens.EditCardScreen
import com.example.nationalmodule1unittest.screens.HomeScreen
import com.example.nationalmodule1unittest.screens.NewCardScreen

@Composable
fun Nav(context: Context) {
    val navController = rememberNavController()
    val currentScreen by navController.currentBackStackEntryFlow.collectAsState(null)
    val navItems =
        mapOf(Screens.單字卡.name to R.drawable.home, Screens.輪轉單字卡.name to R.drawable.mark)

    val room: CardDatabase = Room.databaseBuilder(context, CardDatabase::class.java, "app_db")
        .fallbackToDestructiveMigration().build()
    val card: CardDao = room.cardDao()

    Scaffold(bottomBar = {
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
                    selected = currentScreen?.destination?.route == it.key
                )
            }
        }
    }) { innerPadding ->
        NavHost(navController, startDestination = Screens.單字卡.name) {
            composable(Screens.單字卡.name) { HomeScreen(innerPadding, card, navController) }
            composable(Screens.輪轉單字卡.name) { CardScreen(innerPadding, navController) }
            composable(Screens.NewCard.name) { NewCardScreen(innerPadding, card, navController) }
            composable(
                "${Screens.EditCard.name}/{id}",
                arguments = listOf(navArgument("id") { NavType.IntType })
            ) { EditCardScreen(innerPadding, card, it.arguments?.getInt("id") ?: -1) }
        }
    }
}

enum class Screens {
    單字卡, 輪轉單字卡, NewCard, EditCard
}