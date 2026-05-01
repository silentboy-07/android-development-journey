package com.example.buttons.jetpackcompose


import androidx.compose.ui.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonExample() {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                Toast.makeText(context, "Button Clicked!",Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = buttonColors(
                contentColor = Color.Blue,
                containerColor = Color.Green
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 16.dp,
                pressedElevation = 20.dp,
                hoveredElevation = 16.dp
            ),
            border = BorderStroke(2.dp, color = Color.Blue)

        )

        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Person")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Button")

            }

        }
    }
}


@Composable
fun LoginScreen() {

    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text(text = "Login Here", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            modifier = Modifier.fillMaxWidth(),
            label = {Text(text = "Enter Your Username")},
            shape = RoundedCornerShape(10.dp),
            singleLine = true
            )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email=it},
            modifier = Modifier.fillMaxWidth(),
            label = {Text(text = "Enter Your Email")},
            shape = RoundedCornerShape(10.dp),
            singleLine = true

            )
        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 20.dp
            ),
            enabled = username.isNotEmpty() && email.isNotEmpty()
            )
        {
            Text(text = "Login")
        }
    }
}


@Composable
fun OutlinedButtonExample(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.Yellow
            )
            ) {
            Text(text = "Click Me!")
        }
    }
    
}


@Composable
fun TextButtonExample() {

    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = {}, shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.Yellow
            )
        ) {
            Text(text = "Click Me!", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // also we can make text direct clickable
        Text(text = "Button", modifier = Modifier.clickable(onClick = {}))
    }
    
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun IconButtonExample () {

    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        IconButton( onClick = {}) {
            Icon(imageVector = Icons.Default.Home,
                contentDescription = "Go to Home")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // we can also make icon as a clickable

        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Go to Home",
            modifier = Modifier.clickable(onClick = {}))
    }

}