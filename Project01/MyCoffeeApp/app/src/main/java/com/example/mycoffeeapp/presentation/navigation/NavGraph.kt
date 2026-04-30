package com.example.mycoffeeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mycoffeeapp.presentation.screens.cartscreen.CartScreen
import com.example.mycoffeeapp.presentation.screens.detailsscreen.DetailsScreen
import com.example.mycoffeeapp.presentation.screens.favouritesscreen.FavouritesScreen
import com.example.mycoffeeapp.presentation.screens.homescreen.HomeScreen
import com.example.mycoffeeapp.presentation.screens.profilescreen.ProfileScreen
import com.example.mycoffeeapp.presentation.screens.welcomescreen.WelcomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController =  navController, startDestination = Routes.WelcomeScreen) {
        composable<Routes.WelcomeScreen> { WelcomeScreen(navController) }
        composable<Routes.HomeScreen> { HomeScreen(navController) }
        composable <Routes.DetailScreen>{ backStackEntry ->
            val args = backStackEntry.toRoute<Routes.DetailScreen>()
            DetailsScreen(productId = args.productId, navController)
        }
        composable<Routes.CartScreen> { CartScreen(navController) }
        composable<Routes.FavouritesScreen> { FavouritesScreen(navController) }
        composable<Routes.ProfileScreen> { ProfileScreen(navController) }
    }
}