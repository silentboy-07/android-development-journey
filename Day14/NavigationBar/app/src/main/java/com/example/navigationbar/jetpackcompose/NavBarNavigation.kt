package com.example.navigationbar.jetpackcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NavBarNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavBarRoutes.Home
    ){
        composable<NavBarRoutes.Home>{
            NavBarHomeScreen(navController)
        }

        composable<NavBarRoutes.Search>{
            NavBarSearchScreen(navController)
        }

        composable<NavBarRoutes.Notifications>{
            NavBarNotificationScreen(navController)
        }

        composable<NavBarRoutes.Profile> {
            NavBarProfileScreen(navController)
        }
    }

}