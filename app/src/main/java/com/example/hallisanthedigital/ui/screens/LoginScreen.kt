package com.example.hallisanthedigital.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hallisanthedigital.database.AppDatabase
import com.example.hallisanthedigital.navigation.Screen
import com.example.hallisanthedigital.repository.AppRepository
import com.example.hallisanthedigital.utils.SessionManager
import com.example.hallisanthedigital.viewmodel.AppViewModelFactory
import com.example.hallisanthedigital.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val database = AppDatabase.getDatabase(context)

    val repository = AppRepository(
        database.userDao(),
        database.productDao(),
        database.chatDao()
    )

    val factory = AppViewModelFactory(repository)

    val authViewModel: AuthViewModel =
        viewModel(factory = factory)

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF2E7D32),
                        Color(0xFF66BB6A),
                        Color(0xFFFFF8E1)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(70.dp)
            )

            // App Branding
            Card(
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        Color.White.copy(alpha = 0.9f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 30.dp,
                        vertical = 20.dp
                    ),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "🌾 Halli Santhe",
                        style =
                            MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF1B5E20)
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Village Marketplace",
                        color = Color.Gray
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            // Login Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        Color(0xFFFFFDE7)
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Welcome Back 👋",
                        style =
                            MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF2E7D32)
                    )

                    Spacer(
                        modifier = Modifier.height(25.dp)
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                        },
                        label = {
                            Text("Username")
                        },
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text("Password")
                        },
                        visualTransformation =
                            PasswordVisualTransformation(),
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(25.dp)
                    )

                    Button(
                        onClick = {

                            if (
                                username.isEmpty() ||
                                password.isEmpty()
                            ) {
                                Toast.makeText(
                                    context,
                                    "Enter username and password",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            if (isLoading) return@Button

                            isLoading = true

                            CoroutineScope(
                                Dispatchers.IO
                            ).launch {

                                try {

                                    val user =
                                        authViewModel.loginUser(
                                            username,
                                            password
                                        )

                                    launch(
                                        Dispatchers.Main
                                    ) {

                                        isLoading = false

                                        if (user != null) {

                                            // Existing session data
                                            SessionManager.saveUserSession(
                                                context = context,
                                                username = user.username,
                                                role = user.role,
                                                mobile = user.mobile,
                                                address = user.address
                                            )

                                            Toast.makeText(
                                                context,
                                                "Login Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            if (
                                                user.role == "Seller"
                                            ) {
                                                navController.navigate(
                                                    Screen.SellerDashboard.route
                                                ) {
                                                    popUpTo(
                                                        Screen.Login.route
                                                    ) {
                                                        inclusive = true
                                                    }
                                                }

                                            } else {

                                                navController.navigate(
                                                    Screen.BuyerDashboard.route
                                                ) {
                                                    popUpTo(
                                                        Screen.Login.route
                                                    ) {
                                                        inclusive = true
                                                    }
                                                }
                                            }

                                        } else {

                                            Toast.makeText(
                                                context,
                                                "Invalid Credentials",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                } catch (e: Exception) {

                                    launch(
                                        Dispatchers.Main
                                    ) {

                                        isLoading = false

                                        Toast.makeText(
                                            context,
                                            "Login Failed: ${e.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),

                        shape =
                            RoundedCornerShape(20.dp),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFFFF9800)
                            )
                    ) {

                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier =
                                    Modifier.size(20.dp)
                            )
                        } else {
                            Text("Login")
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    TextButton(
                        onClick = {
                            navController.navigate(
                                Screen.Register.route
                            )
                        },
                        modifier =
                            Modifier.align(
                                Alignment.CenterHorizontally
                            )
                    ) {
                        Text(
                            "New User? / Create Account",
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }
    }
}