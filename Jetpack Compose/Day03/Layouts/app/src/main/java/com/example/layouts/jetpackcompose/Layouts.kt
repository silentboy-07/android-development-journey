package com.example.layouts.jetpackcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RowExample() {

    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "Apple", fontSize = 30.sp,)
        Text(text = "Bannan", fontSize = 30.sp)
        Text(text = "Grapes", fontSize = 30.sp)
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ColumnExample() {

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login Here", fontSize = 30.sp)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {Text(text = "Enter Your Name")},
            shape = CircleShape

        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {Text(text = "Enter Your Email")},
            shape = CircleShape
        )
       
    }}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BoxExample() {

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Note 1",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Text(
            text = "Note 2",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Text(
            text = "Note 3",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.TopStart)
            )

        Text(
            text = "Note 4",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.BottomStart)
        )
        Text(
            text = "Note 5",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            text = "Note 6",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = "Note 7",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }

}