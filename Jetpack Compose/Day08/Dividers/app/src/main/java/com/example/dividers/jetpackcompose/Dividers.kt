package com.example.dividers.jetpackcompose


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun HorizontalDividerExample() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally,

    ) {
        Text(text = "Profile Details")
        HorizontalDivider(
            modifier = Modifier
                .width(150.dp)
                .padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color.Red


        )
        Text(text = "More Information ")
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun VerticalDividerExample() {

    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        Arrangement.Center,
        Alignment.CenterVertically

    )
    {
        Text(text = "Profile Details")
        VerticalDivider(
        modifier = Modifier
                .height(100.dp)
                .padding(16.dp),
            thickness = 2.dp,
            color = Color.Blue
)
        Text(text = "Profile Details")

    }

    
}