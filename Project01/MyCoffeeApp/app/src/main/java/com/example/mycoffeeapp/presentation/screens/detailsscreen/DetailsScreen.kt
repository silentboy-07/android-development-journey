package com.example.mycoffeeapp.presentation.screens.detailsscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycoffeeapp.R
import com.example.mycoffeeapp.domain.model.Product
c
@Composable
fun DetailsScreen() {

    val products = listOf(
        Product(id = 1, name = "Espresso", description = "Strong and Rich", price = 3.80, imageRes = R.drawable.coffee_1),
        Product(id = 2, name = "Cappuccino", description = "Creamy and Smooth", price = 4.20, imageRes = R.drawable.coffee_2),
        Product(id = 3, name = "Latte", description = "Milky and Light", price = 4.50, imageRes = R.drawable.coffee_3),
        Product(id = 4, name = "Americano", description = "Bold and Simple", price = 3.50, imageRes = R.drawable.coffee_4),
        Product(id = 5, name = "Mocha", description = "Chocolate Flavored", price = 4.80, imageRes = R.drawable.coffee_5),
        Product(id = 6, name = "Macchiato", description = "Espresso with Foam", price = 4.00, imageRes = R.drawable.coffee_6),
        Product(id = 7, name = "Flat White", description = "Velvety Smooth", price = 4.30, imageRes = R.drawable.coffee_7),
        Product(id = 8, name = "Cold Brew", description = "Chilled and Refreshing", price = 4.60, imageRes = R.drawable.coffee_8)
    )

    var selectedProduct = products.find { it.id == 1 }

    Scaffold(
        topBar = {DetailsScreenTopAppBar() },
        bottomBar = {DetailsScreenBottomBar() }
    ) {innerPadding ->

        LazyColumn() {
            item {
                ProductDetailsContent(
                    selectedProduct,
                    innerPadding)
            }
        }

    }

}