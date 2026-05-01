package com.example.scaffold.jetpackcompose

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyScreenContent() {

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Box(
                modifier = Modifier.size(90.dp)
                    .background(color = Color.Blue.copy(alpha = 0.2f),
                    shape = CircleShape
                ),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Person,
                    contentDescription = "Person",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Blue
                    )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Text(text = "Vikas Singh",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                    )
                Spacer(modifier = Modifier.height(6.dp))

                Text(text = "The Most Famous Android Development Course of 2026", fontSize = 13.sp, color = Color.DarkGray)
            }
        }

    }
    
}