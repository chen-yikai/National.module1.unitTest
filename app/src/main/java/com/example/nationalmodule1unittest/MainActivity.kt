package com.example.nationalmodule1unittest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.nationalmodule1unittest.ui.theme.Nationalmodule1unitTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Nationalmodule1unitTestTheme {
                val navController = rememberNavController()
                Nav(this, navController)
            }
        }
    }
}