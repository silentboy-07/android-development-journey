package com.example.mycoffeeapp.presentation.screens.favouritesscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycoffeeapp.R
import com.example.mycoffeeapp.domain.model.Product
import com.example.mycoffeeapp.presentation.navigation.Routes
import com.example.mycoffeeapp.presentation.ui_components.MyBottomNavBar

@Composable
fun FavouritesScreen(navController: NavController) {

    var favouriteItems by remember {
        mutableStateOf(
            listOf(
                Product(
                    id = 1,
                    name = "Espresso",
                    description = "Strong and Rich",
                    price = 3.80,
                    imageRes = R.drawable.coffee_1
                ),
                Product(
                    id = 2,
                    name = "Cappuccino",
                    description = "Creamy and Smooth",
                    price = 4.20,
                    imageRes = R.drawable.coffee_2
                ),
                Product(
                    id = 3,
                    name = "Latte",
                    description = "Milky and Light",
                    price = 4.50,
                    imageRes = R.drawable.coffee_3
                ),
            )
        )
    }
    Scaffold(topBar = {FavouritesScreenTopAppBar()},
        bottomBar = { MyBottomNavBar(navController = navController, routes = "Favourites") }
        ) {innerPadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        )
        {
            item {
                favouriteItems.forEach { product ->
                    FavouriteItemCard(product,
                        onRemove = { favouriteItems = favouriteItems - product}
                    )
                }
            }
        }

    }

}


