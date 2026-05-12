package com.example.hallisanthedigital.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.hallisanthedigital.database.AppDatabase
import com.example.hallisanthedigital.navigation.Screen
import com.example.hallisanthedigital.repository.AppRepository
import com.example.hallisanthedigital.viewmodel.AppViewModelFactory
import com.example.hallisanthedigital.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerDashboardScreen(
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

    val productViewModel: ProductViewModel =
        viewModel(factory = factory)

    val products by productViewModel.products.collectAsState()

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    var selectedSort by remember {
        mutableStateOf("Default")
    }

    var categoryExpanded by remember {
        mutableStateOf(false)
    }

    var sortExpanded by remember {
        mutableStateOf(false)
    }

    val categoryOptions = listOf(
        "All",
        "Vegetables",
        "Fruits",
        "Pulses",
        "Grains",
        "Dairy Products",
        "Electronics",
        "Clothing",
        "Books",
        "Handmade Crafts",
        "Home Essentials",
        "Livestock Products",
        "Others"
    )

    val sortOptions = listOf(
        "Default",
        "Price Low to High",
        "Price High to Low"
    )

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }

    val filteredProducts = products
        .filter { product ->

            val searchMatch =
                product.name.contains(
                    searchText,
                    ignoreCase = true
                )

            val categoryMatch =
                selectedCategory == "All" ||
                        product.category == selectedCategory

            searchMatch && categoryMatch
        }
        .let { list ->

            when (selectedSort) {

                "Price Low to High" -> {
                    list.sortedBy {
                        it.price.toDoubleOrNull() ?: 0.0
                    }
                }

                "Price High to Low" -> {
                    list.sortedByDescending {
                        it.price.toDoubleOrNull() ?: 0.0
                    }
                }

                else -> list
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1B5E20),
                        Color(0xFF43A047),
                        Color(0xFFE8F5E9)
                    )
                )
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 50.dp,
                bottom = 16.dp
            )
    ) {

        //---------------- HEADER ----------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "🌾 Halli Santhe",
                    style =
                        MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Buy Fresh Local Products",
                    color = Color.White
                )
            }

            IconButton(
                onClick = {
                    navController.navigate(
                        Screen.Profile.route
                    )
                }
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //---------------- SEARCH BAR ----------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                placeholder = {
                    Text(
                        "Search village products..."
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedBorderColor =
                            Color.Transparent,
                        unfocusedBorderColor =
                            Color.Transparent
                    )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //---------------- FILTER SECTION ----------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            // Category Dropdown
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = {
                        categoryExpanded =
                            !categoryExpanded
                    }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Category")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedBorderColor =
                                    Color.Transparent,
                                unfocusedBorderColor =
                                    Color.Transparent
                            )
                    )

                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = {
                            categoryExpanded = false
                        }
                    ) {
                        categoryOptions.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Text(category)
                                },
                                onClick = {
                                    selectedCategory =
                                        category
                                    categoryExpanded =
                                        false
                                }
                            )
                        }
                    }
                }
            }

            // Sort Dropdown
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = sortExpanded,
                    onExpandedChange = {
                        sortExpanded =
                            !sortExpanded
                    }
                ) {
                    OutlinedTextField(
                        value = selectedSort,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Sort By")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedBorderColor =
                                    Color.Transparent,
                                unfocusedBorderColor =
                                    Color.Transparent
                            )
                    )

                    ExposedDropdownMenu(
                        expanded = sortExpanded,
                        onDismissRequest = {
                            sortExpanded = false
                        }
                    ) {
                        sortOptions.forEach { sort ->
                            DropdownMenuItem(
                                text = {
                                    Text(sort)
                                },
                                onClick = {
                                    selectedSort =
                                        sort
                                    sortExpanded =
                                        false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Available Products",
            style =
                MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        //---------------- PRODUCT GRID ----------------
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment =
                    Alignment.Center
            ) {
                Text(
                    text = "No Products Found",
                    color = Color.White
                )
            }
        } else {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {

                items(filteredProducts) { product ->

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(
                                    "${Screen.ProductDetail.route}/${product.id}"
                                )
                            },
                        shape =
                            RoundedCornerShape(20.dp),
                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            ),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            )
                    ) {

                        Column {

                            Image(
                                painter =
                                    rememberAsyncImagePainter(
                                        if (
                                            product.imageUri.isNotEmpty()
                                        )
                                            product.imageUri
                                        else
                                            "https://via.placeholder.com/300"
                                    ),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 20.dp,
                                            topEnd = 20.dp
                                        )
                                    ),
                                contentScale =
                                    ContentScale.Crop
                            )

                            Column(
                                modifier =
                                    Modifier.padding(12.dp)
                            ) {

                                Text(
                                    text =
                                        product.name,
                                    fontWeight =
                                        FontWeight.Bold,
                                    color =
                                        Color(
                                            0xFF1B5E20
                                        )
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            4.dp
                                        )
                                )

                                Text(
                                    text =
                                        "₹${product.price}",
                                    color =
                                        Color(
                                            0xFFFF6F00
                                        ),
                                    fontWeight =
                                        FontWeight.Bold
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            6.dp
                                        )
                                )

                                Surface(
                                    shape =
                                        RoundedCornerShape(
                                            50.dp
                                        ),
                                    color =
                                        Color(
                                            0xFFE8F5E9
                                        )
                                ) {
                                    Text(
                                        text =
                                            product.category,
                                        color =
                                            Color(
                                                0xFF2E7D32
                                            ),
                                        modifier =
                                            Modifier.padding(
                                                horizontal = 10.dp,
                                                vertical = 4.dp
                                            )
                                    )
                                }

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            6.dp
                                        )
                                )

                                Text(
                                    text =
                                        "🌾 Halli Fresh",
                                    color =
                                        Color.Gray,
                                    style =
                                        MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}