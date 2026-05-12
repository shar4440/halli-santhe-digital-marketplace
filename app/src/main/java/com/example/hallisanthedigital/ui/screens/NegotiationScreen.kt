package com.example.hallisanthedigital.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.hallisanthedigital.database.AppDatabase
import com.example.hallisanthedigital.model.ChatMessage
import com.example.hallisanthedigital.repository.AppRepository
import com.example.hallisanthedigital.utils.SessionManager
import com.example.hallisanthedigital.viewmodel.AppViewModelFactory
import com.example.hallisanthedigital.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NegotiationScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry
) {

    val context = LocalContext.current

    val productId =
        backStackEntry.arguments
            ?.getString("productId")
            ?.toIntOrNull() ?: 0

    val database = AppDatabase.getDatabase(context)

    val repository = AppRepository(
        database.userDao(),
        database.productDao(),
        database.chatDao()
    )

    val factory = AppViewModelFactory(repository)

    val productViewModel: ProductViewModel =
        viewModel(factory = factory)

    val chatMessages by
    productViewModel.chatMessages.collectAsState()

    val product by
    productViewModel.selectedProduct.collectAsState()

    var messageText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        productViewModel.getProductById(productId)
        productViewModel.loadChatMessages(productId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1B5E20),
                        Color(0xFF4CAF50),
                        Color(0xFFE8F5E9)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // Header moved downward
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 45.dp,
                        bottom = 12.dp
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Negotiation Chat",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    product?.let {
                        Text(
                            text = "Product: ${it.name}",
                            color = Color.Gray
                        )
                    }
                }
            }

            // Messages at bottom
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),

                verticalArrangement = Arrangement.Bottom
            ) {

                items(chatMessages) { chat ->

                    val isCurrentUser =
                        chat.senderName ==
                                SessionManager.getUsername(context)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            if (isCurrentUser)
                                Arrangement.End
                            else
                                Arrangement.Start
                    ) {

                        Card(
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                                .widthIn(max = 280.dp),

                            shape = RoundedCornerShape(20.dp),

                            colors = CardDefaults.cardColors(
                                containerColor =
                                    if (isCurrentUser)
                                        Color(0xFFDCF8C6)
                                    else
                                        Color.White
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {

                                Text(
                                    text = buildString {
                                        append(chat.senderName)

                                        if (
                                            chat.senderName ==
                                            product?.sellerName
                                        ) {
                                            append(" (Seller)")
                                        } else {
                                            append(" (Buyer)")
                                        }
                                    },
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = chat.message
                                )
                            }
                        }
                    }
                }
            }

            // Bottom input section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    OutlinedTextField(
                        value = messageText,
                        onValueChange = {
                            messageText = it
                        },
                        placeholder = {
                            Text("Negotiate your price...")
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    FloatingActionButton(
                        onClick = {

                            if (messageText.isNotEmpty()) {

                                val message = ChatMessage(
                                    productId = productId,
                                    senderName =
                                        SessionManager.getUsername(context),
                                    message = messageText
                                )

                                productViewModel.sendMessage(
                                    message
                                )

                                messageText = ""
                            }
                        },
                        containerColor =
                            Color(0xFF2E7D32),
                        shape = CircleShape
                    ) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}


