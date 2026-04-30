package com.example.mycoffeeapp.presentation.screens.favouritesscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycoffeeapp.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FavouritesScreenTopAppBar() {

    TopAppBar(
        title = { Text(text = "Favourites",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold

        ) },
    )
}
