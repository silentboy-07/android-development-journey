package com.example.mycoffeeapp.presentation.screens.cartscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycoffeeapp.R
import com.example.mycoffeeapp.domain.model.Product
import com.example.mycoffeeapp.presentation.theme.LightBrown
import com.example.mycoffeeapp.presentation.ui_components.MyBottomNavBar

@Composable
fun CartScreen(navController: NavController) {

    val cartProducts = listOf(
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

    var amount by remember { mutableStateOf(12.50) }
    var deliveryFee by remember { mutableStateOf(1.0) }

    var totalAmount by remember { mutableStateOf(amount + deliveryFee) }

    Scaffold(
        topBar = { CartScreenTopBar(navController) },
        bottomBar = { MyBottomNavBar(navController = navController, "Cart") }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier

                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            item {
                Row() {
                    Text(
                        text = "Deliver",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightBrown
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                cartProducts.forEach { product ->
                    CartItemCard(product)
                }



                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Payment Summary",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Price", fontSize = 18.sp)
                    Text(text = "$ $amount", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Delivery Fee", fontSize = 18.sp)
                    Text(text = "$ $deliveryFee", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                PaymentModeSelectionCard(totalAmount)

                Spacer(modifier = Modifier.height(80.dp))

            }
        }

    }
}