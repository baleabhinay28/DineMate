
package com.example.dinemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

import com.example.dinemate.ui.screens.SplashScreen
import com.example.dinemate.ui.screens.LoginScreen
import com.example.dinemate.ui.screens.SignUpScreen
import com.example.dinemate.ui.screens.HomeScreen
import com.example.dinemate.ui.screens.RestaurantDetailsScreen
import com.example.dinemate.ui.theme.DineMateTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DineMateTheme {

                val auth = remember {
                    FirebaseAuth.getInstance()
                }

                // Available screens
                val loginScreen = "login"
                val signUpScreen = "signup"
                val homeScreen = "home"
                val detailsScreen = "details"

                var showSplash by rememberSaveable {
                    mutableStateOf(value = true)
                }

                var currentScreen by rememberSaveable {
                    mutableStateOf(
                        if (auth.currentUser != null) {
                            homeScreen
                        } else {
                            loginScreen
                        },
                    )
                }

                var selectedRestaurant by rememberSaveable {
                    mutableStateOf<String?>(null)
                }

                // Display splash screen for 2 seconds
                LaunchedEffect(showSplash) {
                    if (showSplash) {
                        delay(2.seconds)
                        showSplash = false
                    }
                }

                // Handle Android back button
                BackHandler(
                    enabled = !showSplash && (currentScreen == detailsScreen || currentScreen == signUpScreen),
                ) {
                    when (currentScreen) {
                        detailsScreen -> {
                            selectedRestaurant = null
                            currentScreen = homeScreen
                        }

                        signUpScreen -> {
                            currentScreen = loginScreen
                        }
                    }
                }

                if (showSplash) {

                    SplashScreen()

                } else {

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color(0xFFFFF8F2),
                    ) { innerPadding ->

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {

                            when (currentScreen) {

                                loginScreen -> {
                                    LoginScreen(
                                        onLoginSuccess = {
                                            currentScreen = homeScreen
                                        },
                                        onSignUpClick = {
                                            currentScreen = signUpScreen
                                        },
                                    )
                                }

                                signUpScreen -> {
                                    SignUpScreen(
                                        onSignUpSuccess = {
                                            currentScreen = homeScreen
                                        },
                                        onLoginClick = {
                                            currentScreen = loginScreen
                                        },
                                    )
                                }

                                homeScreen -> {
                                    HomeScreen(
                                        onRestaurantClick = { restaurant ->
                                            selectedRestaurant = restaurant
                                            currentScreen = detailsScreen
                                        },
                                    )
                                }

                                detailsScreen -> {
                                    RestaurantDetailsScreen(
                                        restaurantName =
                                            selectedRestaurant.orEmpty(),
                                        onBackClick = {
                                            selectedRestaurant = null
                                            currentScreen = homeScreen
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
