package com.example.mycoffeeapp.presentation.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycoffeeapp.R
import com.example.mycoffeeapp.domain.model.Product
import com.example.mycoffeeapp.presentation.ui_components.MyBottomNavBar

@Composable
fun HomeScreen(navController: NavController) {

    val location = "Rajkot, Gujarat"

    Scaffold(
        bottomBar = { MyBottomNavBar(navController, "Home")}
    ) {innerPadding->
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f/3f)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF303030),
                        Color(0xFF1F1F1F),
                        Color(0xFF121212)
                    )
                )
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(innerPadding),
        ) {


            // Displaying Products
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
            ProductsGrid(products = products, navController = navController) {

                Text(text = "Location",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(location,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Icon(imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Change Location",
                        tint = Color.White
                    )

                }
                Spacer(modifier = Modifier.height(20.dp))
                MySearchBar()

                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painter = painterResource(R.drawable.banner_1),
                    contentDescription = "Home Banner",
                )
                Spacer(modifier = Modifier.height(16.dp))
                HomeScreenCategories()

            }
        }
    }

}