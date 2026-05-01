package com.example.activitylifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.activitylifecycle.ui.theme.ActivityLifecycleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate() called!")
        enableEdgeToEdge()
        setContent {
            ActivityLifecycleTheme {
                   ActivityLifecycleExample()
                }
            }
        }

    override fun onStart() {
        super.onStart()
        println("onStart() called!")
    }

    override fun onResume() {
        super.onResume()
        println("onResume() called!")
    }

    override fun onPause() {
        super.onPause()
        println("onPause() called!")
    }

    override fun onStop() {
        super.onStop()
        println("onStop() called!")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart() called!")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy() called!")
    }
    }



