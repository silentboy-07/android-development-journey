package com.example.sharedpreferance

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit


@Composable
fun SharedPreferences(context: Context) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var savedData by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Hello Guys!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            sharedPref.edit {
                putString("username", username)
                putString("password", password)
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Save Data")

        }

        Button(onClick = {
            val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            sharedPref.edit { clear() }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Remove Data")

        }
        Button(onClick = {savedData =""}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Hide Data")

        }

        Button(onClick = {
            val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val savedUsername = sharedPref.getString("username","No Username!")
            val savedPassword = sharedPref.getString("password","No Password!")
            savedData = "Username: $savedUsername\nPassword: $savedPassword"
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Show Data")
        }
        if (savedData.isNotEmpty()) {
            Text(text = savedData)
        }
    }

}