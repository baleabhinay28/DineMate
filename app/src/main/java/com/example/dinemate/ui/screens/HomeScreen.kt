
package com.example.dinemate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {

    var searchText by remember { mutableStateOf("") }

    val orange = Color(0xFFFF7043)
    val background = Color(0xFFFFF8F2)

    val restaurants = listOf(
        Triple("🍕 Pizza Palace", "Italian • Pizza", "⭐ 4.5"),
        Triple("🍔 Burger House", "Fast Food • Burgers", "⭐ 4.3"),
        Triple("🍜 Noodle Corner", "Asian • Noodles", "⭐ 4.7")
    )

    val filteredRestaurants = restaurants.filter {
        it.first.contains(searchText, ignoreCase = true) ||
                it.second.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "DineMate 🍽️",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = orange
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Discover your next favourite meal!",
            fontSize = 15.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = {
                Text("Search restaurants or cuisines...")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = orange,
                cursorColor = orange
            )
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Popular Restaurants",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredRestaurants) { restaurant ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = restaurant.first,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = restaurant.second,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = restaurant.third,
                            fontSize = 14.sp,
                            color = orange,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
