package com.example.navigationbar.jetpackcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NavBarProfileScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {MyNavBar(navController, "Profile") }
    ) {innerPadding->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(text = "Profile Screen",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}