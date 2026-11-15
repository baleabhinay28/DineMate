
package com.example.dinemate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RestaurantDetailsScreen(
    restaurantName: String,
    onBackClick: () -> Unit,
) {
    val orange = Color(0xFFFF7043)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F2))
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("← Back", color = orange)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "🍽️",
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = restaurantName,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Welcome! Explore delicious food.",
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Restaurant Information",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("⭐ Rating: 4.5 (Sample)")
                Text("🕒 Opening Hours: 10 AM - 10 PM (Sample)")
                Text("📍 Location: Available soon")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = orange
            )
        ) {
            Text("View Menu - Coming Soon")
        }
    }
}
