package com.example.nationalmodule1unittest

import android.util.Log
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class StartTesting {
    @get:Rule
    val rule = createComposeRule()

    fun running(test:Int){
        Log.d("test55","步驟 $test 將執行...")
    }

    @Test
    fun test55() {

    }
}