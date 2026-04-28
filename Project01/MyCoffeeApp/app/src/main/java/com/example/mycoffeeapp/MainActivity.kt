package com.example.mycoffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mycoffeeapp.presentation.screens.homescreen.HomeScreen
import com.example.mycoffeeapp.presentation.theme.MyCoffeeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCoffeeAppTheme {
                HomeScreen()
            }
        }
    }
}

