package com.example.mycoffeeapp.presentation.ui_components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycoffeeapp.R
import com.example.mycoffeeapp.presentation.navigation.Routes
import com.example.mycoffeeapp.presentation.theme.LightBrown

@Composable
fun MyBottomNavBar(navController: NavController, routes: String) {

    //Bottom Nav Items
    val navItems = listOf(
        NavItem(title = "Home", R.drawable.regular_outline_home, Routes.HomeScreen),
        NavItem(title = "Cart", R.drawable.regular_outline_bag, Routes.CartScreen),
        NavItem(title = "Favourites", R.drawable.regular_outline_heart, Routes.FavouritesScreen),
        NavItem(title = "Profile", R.drawable.outline_account_circle_24, Routes.ProfileScreen)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.height(80.dp)
    ) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = { Text(item.title) },
                // Handling bottom bar Navigation
                onClick = {
                    navController.navigate(item.routes){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                selected = item.title == routes,
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LightBrown,
                    selectedTextColor = LightBrown,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = LightBrown.copy(alpha = 0.05f)
                )
            )
        }
    }
}

data class NavItem(
    val title: String,
    val icon: Int,
    val routes: Routes

    )