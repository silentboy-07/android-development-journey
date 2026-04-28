package com.example.mycoffeeapp.presentation.screens.homescreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mycoffeeapp.domain.model.Product
import com.example.mycoffeeapp.presentation.screens.ProductCard

@Composable
fun ProductsGrid(
    products: List<Product>,
    topContent: @Composable () -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item { topContent() }

        items(products.chunked(2)) { rowItems ->

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                ProductCard(
                    product = rowItems[0],
                    modifier = Modifier.weight(1f)
                )

                if (rowItems.size == 2) {
                    ProductCard(
                        product = rowItems[1],
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }


        }
    }

}