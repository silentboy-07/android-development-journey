package com.example.navigationbar.jetpackcompose


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController


@Composable
fun MyNavBar(navController: NavHostController, key: String) {

    val navItems = listOf(
        NavItem(title = "Home", Icons.Default.Home, NavBarRoutes.Home),
        NavItem(title = "Search", Icons.Default.Search, NavBarRoutes.Search),
        NavItem(title = "Notification", Icons.Default.Notifications, NavBarRoutes.Notifications),
        NavItem(title = "Profile", Icons.Default.Person, NavBarRoutes.Profile)
    )
    NavigationBar() {

        navItems.forEach { item ->
            NavigationBarItem(
                selected = item.title == key,
                onClick = {navController.navigate(item.routes){
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                          },
                icon = {
                    Icon(imageVector = item.icon,
                        contentDescription = "Home")
                },
                label = { Text(text = item.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color.Blue,
                    selectedIconColor = Color.Blue,
                    indicatorColor = Color.Blue.copy(alpha = 0.1f),
                    unselectedTextColor = Color.DarkGray,
                    unselectedIconColor = Color.DarkGray
                )
            )
        }
    }
}
data class NavItem(
    val title: String,
    val icon: ImageVector,
    val routes: NavBarRoutes

)