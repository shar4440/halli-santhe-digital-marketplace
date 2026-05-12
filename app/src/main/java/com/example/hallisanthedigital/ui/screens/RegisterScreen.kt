package com.example.hallisanthedigital.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.example.hallisanthedigital.viewmodel.AppViewModelFactory
import com.example.hallisanthedigital.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val database =
        AppDatabase.getDatabase(context)

    val repository = AppRepository(
        database.userDao(),
        database.productDao(),
        database.chatDao()
    )

    val factory =
        AppViewModelFactory(repository)

    val authViewModel: AuthViewModel =
        viewModel(factory = factory)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var selectedRole by remember {
        mutableStateOf("Buyer")
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF2E7D32),   // Dark Green
                        Color(0xFF66BB6A),   // Light Green
                        Color(0xFFFFF8E1)    // Cream
                    )
                )
            )
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.Center),

            shape = RoundedCornerShape(30.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFDE7)
            )
        ) {

            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {

                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Username
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = {
                        Text("Username")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF388E3C),
                        unfocusedBorderColor = Color(0xFF81C784),
                        focusedLabelColor = Color(0xFF2E7D32),
                        cursorColor = Color(0xFF2E7D32)
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text("Password")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            null
                        )
                    },
                    visualTransformation =
                        PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF388E3C),
                        unfocusedBorderColor = Color(0xFF81C784),
                        focusedLabelColor = Color(0xFF2E7D32),
                        cursorColor = Color(0xFF2E7D32)
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Mobile
                OutlinedTextField(
                    value = mobile,
                    onValueChange = {
                        mobile = it
                    },
                    label = {
                        Text("Mobile Number")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Phone,
                            null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF388E3C),
                        unfocusedBorderColor = Color(0xFF81C784),
                        focusedLabelColor = Color(0xFF2E7D32),
                        cursorColor = Color(0xFF2E7D32)
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Address
                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                    },
                    label = {
                        Text("Address")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Home,
                            null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF388E3C),
                        unfocusedBorderColor = Color(0xFF81C784),
                        focusedLabelColor = Color(0xFF2E7D32),
                        cursorColor = Color(0xFF2E7D32)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Choose Role",
                    color = Color(0xFF2E7D32),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    FilterChip(
                        selected =
                            selectedRole == "Seller",
                        onClick = {
                            selectedRole = "Seller"
                        },
                        label = {
                            Text("Seller")
                        },
                        colors =
                            FilterChipDefaults.filterChipColors(
                                selectedContainerColor =
                                    Color(0xFF4CAF50),
                                selectedLabelColor =
                                    Color.White
                            )
                    )

                    FilterChip(
                        selected =
                            selectedRole == "Buyer",
                        onClick = {
                            selectedRole = "Buyer"
                        },
                        label = {
                            Text("Buyer")
                        },
                        colors =
                            FilterChipDefaults.filterChipColors(
                                selectedContainerColor =
                                    Color(0xFFFF9800),
                                selectedLabelColor =
                                    Color.White
                            )
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {

                        if (
                            username.isNotEmpty() &&
                            password.isNotEmpty() &&
                            mobile.isNotEmpty() &&
                            address.isNotEmpty()
                        ) {

                            scope.launch {

                                val isRegistered =
                                    withContext(
                                        kotlinx.coroutines.Dispatchers.IO
                                    ) {
                                        authViewModel.registerUser(
                                            username = username,
                                            password = password,
                                            mobile = mobile,
                                            address = address,
                                            role = selectedRole
                                        )
                                    }

                                if (isRegistered) {

                                    Toast.makeText(
                                        context,
                                        "Account Created Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate(
                                        Screen.Login.route
                                    ) {
                                        popUpTo(
                                            Screen.Register.route
                                        ) {
                                            inclusive = true
                                        }
                                    }

                                } else {

                                    Toast.makeText(
                                        context,
                                        "Username already exists",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        } else {

                            Toast.makeText(
                                context,
                                "Fill all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(20.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF2E7D32)
                    )
                ) {
                    Text("Create Account")
                }
            }
        }
    }
}