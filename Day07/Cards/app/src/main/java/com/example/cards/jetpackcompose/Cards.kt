package com.example.cards.jetpackcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cards.R


@Composable
fun CardExample() {

    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                Color.Blue,
                contentColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 20.dp
            ),
            border = BorderStroke(width = 3.dp, color = Color.Green)
        ) {
            Text(
                text = "This is a Card!",
                fontSize = 30.sp,
                modifier = Modifier.padding(16.dp))
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardExample02() {

    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            )

        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            color = Color.LightGray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Person,
                        contentDescription = "person",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Gray
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(text = "Vikas Singh",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Hey I am Vikas and I am A Android Developer and I also know flutter framework.",
                        fontSize = 13.sp,
                        color = Color.DarkGray

                    )
                }
            }
        }
    }
    
}