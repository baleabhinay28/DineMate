
package com.example.dinemate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(value = false) }
    var errorMessage by remember { mutableStateOf("") }

    val orange = Color(0xFFFF7043)

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFC46B),
            Color(0xFFFF8A50),
            orange
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "🍽️",
                fontSize = 50.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Join DineMate",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Create your account to get started",
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Create Account",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = orange
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Full name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            errorMessage = ""
                        },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            errorMessage = ""
                        },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = ""
                        },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation =
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Confirm password
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            errorMessage = ""
                        },
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation =
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Firebase registration
                    Button(
                        onClick = {

                            val userName = fullName.trim()
                            val userEmail = email.trim()

                            when {
                                userName.isEmpty() -> {
                                    errorMessage =
                                        "Please enter your full name"
                                }

                                userEmail.isEmpty() ||
                                        !android.util.Patterns.EMAIL_ADDRESS
                                            .matcher(userEmail).matches() -> {
                                    errorMessage =
                                        "Please enter a valid email address"
                                }

                                password.length < 6 -> {
                                    errorMessage =
                                        "Password must contain at least 6 characters"
                                }

                                password != confirmPassword -> {
                                    errorMessage =
                                        "Passwords do not match"
                                }

                                else -> {
                                    isLoading = true
                                    errorMessage = ""

                                    val auth = FirebaseAuth.getInstance()

                                    auth.createUserWithEmailAndPassword(
                                        userEmail,
                                        password
                                    ).addOnCompleteListener { task ->

                                        if (task.isSuccessful) {

                                            val profileUpdate =
                                                UserProfileChangeRequest
                                                    .Builder()
                                                    .setDisplayName(userName)
                                                    .build()

                                            val user = auth.currentUser

                                            if (user != null) {
                                                user.updateProfile(
                                                    profileUpdate
                                                ).addOnCompleteListener {
                                                    isLoading = false
                                                    onSignUpSuccess()
                                                }
                                            } else {
                                                isLoading = false
                                                errorMessage =
                                                    "Account created. Please log in."
                                            }

                                        } else {
                                            isLoading = false
                                            errorMessage =
                                                task.exception
                                                    ?.localizedMessage
                                                    ?: "Registration failed. Please try again."
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = orange
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Create Account",
                                fontSize = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = onLoginClick
                    ) {
                        Text(
                            text = "Already have an account? Login",
                            color = orange
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}
