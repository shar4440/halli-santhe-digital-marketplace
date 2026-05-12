package com.example.hallisanthedigital.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.hallisanthedigital.database.AppDatabase
import com.example.hallisanthedigital.navigation.Screen
import com.example.hallisanthedigital.repository.AppRepository
import com.example.hallisanthedigital.utils.SessionManager
import com.example.hallisanthedigital.viewmodel.AppViewModelFactory
import com.example.hallisanthedigital.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreen(
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

    val product by productViewModel.selectedProduct.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.getProductById(productId)
    }

    product?.let { currentProduct ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF4E342E),
                            Color(0xFF2E7D32),
                            Color(0xFFFFB300)
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 50.dp,
                        bottom = 20.dp
                    )
            ) {

                // Product Image
                Card(
                    shape = RoundedCornerShape(25.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF8E1)
                    )
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model =
                                if (
                                    currentProduct.imageUri.isNotEmpty()
                                )
                                    currentProduct.imageUri
                                else
                                    "https://via.placeholder.com/400"
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Product Name
                Text(
                    text = currentProduct.name,
                    style =
                        MaterialTheme.typography
                            .headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                // Price
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor =
                            Color(0xFFFF9800)
                    ),
                    shape =
                        RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text =
                            "₹${currentProduct.price}",
                        modifier =
                            Modifier.padding(
                                horizontal = 18.dp,
                                vertical = 10.dp
                            ),
                        color = Color.White,
                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text =
                        "🌾 Fresh From Halli Santhe",
                    color =
                        Color(0xFFFFF8E1),
                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Info Cards
                ProductInfoCard(
                    icon = Icons.Default.Info,
                    title = "Category",
                    value = currentProduct.category
                )

                ProductInfoCard(
                    icon = Icons.Default.Person,
                    title = "Seller",
                    value = currentProduct.sellerName
                )

                ProductInfoCard(
                    icon =
                        Icons.Default.ShoppingCart,
                    title = "Stock",
                    value = currentProduct.stock
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Description
                Card(
                    shape =
                        RoundedCornerShape(20.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(0xFFFFF8E1)
                        )
                ) {
                    Column(
                        modifier =
                            Modifier.padding(
                                16.dp
                            )
                    ) {
                        Text(
                            text =
                                "Product Description",
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color(
                                    0xFF4E342E
                                )
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
                        )

                        Text(
                            text =
                                currentProduct.description,
                            color =
                                Color.DarkGray
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                // Button Logic SAME
                if (
                    SessionManager.getUsername(context) ==
                    currentProduct.sellerName
                ) {

                    Button(
                        onClick = {
                            navController.navigate(
                                "${Screen.Negotiation.route}/${currentProduct.id}"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape =
                            RoundedCornerShape(
                                20.dp
                            ),
                        colors =
                            ButtonDefaults
                                .buttonColors(
                                    containerColor =
                                        Color(
                                            0xFF2E7D32
                                        )
                                )
                    ) {
                        Icon(
                            Icons.AutoMirrored
                                .Filled.Send,
                            null
                        )

                        Spacer(
                            modifier =
                                Modifier.width(
                                    8.dp
                                )
                        )

                        Text(
                            "View Messages"
                        )
                    }

                } else {

                    Button(
                        onClick = {
                            navController.navigate(
                                "${Screen.Negotiation.route}/${currentProduct.id}"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape =
                            RoundedCornerShape(
                                20.dp
                            ),
                        colors =
                            ButtonDefaults
                                .buttonColors(
                                    containerColor =
                                        Color(
                                            0xFFFF9800
                                        )
                                )
                    ) {
                        Icon(
                            Icons.AutoMirrored
                                .Filled.Send,
                            null
                        )

                        Spacer(
                            modifier =
                                Modifier.width(
                                    8.dp
                                )
                        )

                        Text(
                            "Contact Seller"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductInfoCard(
    icon: ImageVector,
    title: String,
    value: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                Color(0xFFFFF8E1)
        )
    ) {

        Row(
            modifier =
                Modifier.padding(16.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF2E7D32)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = title,
                    color = Color.Gray
                )

                Text(
                    text = value,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(0xFF4E342E)
                )
            }
        }
    }
}