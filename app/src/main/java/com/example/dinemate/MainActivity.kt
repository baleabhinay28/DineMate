
package com.example.dinemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

import com.example.dinemate.ui.screens.SplashScreen
import com.example.dinemate.ui.screens.HomeScreen
import com.example.dinemate.ui.screens.RestaurantDetailsScreen
import com.example.dinemate.ui.theme.DineMateTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DineMateTheme {

                var showSplash by rememberSaveable {
                    mutableStateOf(true)
                }

                var selectedRestaurant by rememberSaveable {
                    mutableStateOf<String?>(null)
                }

                LaunchedEffect(showSplash) {
                    if (showSplash) {
                        delay(2000)
                        showSplash = false
                    }
                }

                BackHandler(
                    enabled = !showSplash &&
                            selectedRestaurant != null
                ) {
                    selectedRestaurant = null
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color(0xFFFFF8F2)
                    ) { innerPadding ->

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            if (selectedRestaurant == null) {
                                HomeScreen(
                                    onRestaurantClick = { restaurant ->
                                        selectedRestaurant = restaurant
                                    }
                                )
                            } else {
                                RestaurantDetailsScreen(
                                    restaurantName =
                                        selectedRestaurant!!,
                                    onBackClick = {
                                        selectedRestaurant = null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
