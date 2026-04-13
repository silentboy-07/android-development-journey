package com.example.basiccomposable.jetpackcompose

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable

fun TextExample() {

    Text(
        text = "Mission Android 2026",
        color = Color.Blue,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        letterSpacing = 2.sp,
        textAlign = TextAlign.End,
        textDecoration = TextDecoration.Underline
    )
}

@Preview(showBackground = false, showSystemUi = false)
@Composable
fun TextFieldExample() {

    var name by remember() { mutableStateOf(value = "") }

    TextField(
        value = name,
        onValueChange = { name = it },
        label = { Text(text = "Enter Your Name") },
//        placeholder = {Text(text = "Enter Your Name")}
        leadingIcon = { Text(text = "*", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        trailingIcon = {Text(text = "#", fontSize = 20.sp, fontWeight = FontWeight.Bold)},
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.Red,
            focusedTextColor = Color.Blue,
            focusedLabelColor = Color.Yellow,
            unfocusedLabelColor = Color.Green,
            focusedLeadingIconColor = Color.Magenta,
            unfocusedLeadingIconColor = Color.DarkGray,
            focusedContainerColor = Color.Blue,
            unfocusedContainerColor = Color.Magenta,
            focusedIndicatorColor = Color.Yellow,
            unfocusedIndicatorColor = Color.Red
        )


    )

}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OutlinedTextFieldExample() {

    var name by remember { mutableStateOf(value ="") }
    OutlinedTextField(
        value = name,
        onValueChange = {name = it},
        label = {Text(text = "Enter Your Name")},
        shape = CircleShape,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Red,
            focusedTextColor = Color.Blue,
            focusedContainerColor = Color.Gray,
            unfocusedContainerColor = Color.Magenta,
        )
    )

}