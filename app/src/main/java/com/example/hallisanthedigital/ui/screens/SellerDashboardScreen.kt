package com.example.hallisanthedigital.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.hallisanthedigital.database.AppDatabase
import com.example.hallisanthedigital.model.Product
import com.example.hallisanthedigital.navigation.Screen
import com.example.hallisanthedigital.repository.AppRepository
import com.example.hallisanthedigital.utils.SessionManager
import com.example.hallisanthedigital.viewmodel.AppViewModelFactory
import com.example.hallisanthedigital.viewmodel.ProductViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardScreen(
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

    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productStock by remember { mutableStateOf("") }

    var showAddDialog by remember {
        mutableStateOf(false)
    }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var cameraImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    //---------------- Gallery ----------------
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            selectedImageUri = uri
        }

    //---------------- Camera ----------------
    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                selectedImageUri = cameraImageUri
            }
        }

    //---------------- Camera Permission ----------------
    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                try {
                    val imageFile = File(
                        context.externalCacheDir,
                        "product_${System.currentTimeMillis()}.jpg"
                    )

                    if (!imageFile.exists()) {
                        imageFile.createNewFile()
                    }

                    val uri =
                        FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.provider",
                            imageFile
                        )

                    cameraImageUri = uri
                    cameraLauncher.launch(uri)

                } catch (e: Exception) {

                    Toast.makeText(
                        context,
                        "Camera Failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {

                Toast.makeText(
                    context,
                    "Camera permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }

    val sellerProducts = products.filter {
        it.sellerName == SessionManager.getUsername(context)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1B5E20),
                        Color(0xFF43A047),
                        Color(0xFFFFF8E1)
                    )
                )
            )
            .padding(16.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(35.dp))

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
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style =
                            MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text =
                            "Welcome ${SessionManager.getUsername(context)}",
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
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    showAddDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sell New Product")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "My Products",
                color = Color.White,
                style =
                    MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(650.dp)
            ) {

                items(sellerProducts) { product ->

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(
                                    "${Screen.ProductDetail.route}/${product.id}"
                                )
                            },
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                Color(0xFFFFF3E0)
                        )
                    ) {

                        Column(
                            modifier =
                                Modifier.fillMaxWidth()
                        ) {

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
                                    .height(130.dp),
                                contentScale =
                                    ContentScale.Crop
                            )

                            Column(
                                modifier =
                                    Modifier.padding(12.dp)
                            ) {

                                Text(
                                    text = product.name,
                                    color =
                                        Color(0xFF1B5E20),
                                    fontWeight =
                                        FontWeight.Bold
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(6.dp)
                                )

                                Text(
                                    text =
                                        "₹${product.price}",
                                    color =
                                        Color(0xFFFF6F00),
                                    fontWeight =
                                        FontWeight.Bold
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(6.dp)
                                )

                                Surface(
                                    shape =
                                        RoundedCornerShape(
                                            50.dp
                                        ),
                                    color =
                                        Color(
                                            0xFF2E7D32
                                        )
                                ) {
                                    Text(
                                        text =
                                            "View Product",
                                        color =
                                            Color.White,
                                        modifier =
                                            Modifier.padding(
                                                horizontal = 10.dp,
                                                vertical = 4.dp
                                            )
                                    )
                                }

                                Spacer(
                                    modifier =
                                        Modifier.height(8.dp)
                                )

//                                Text(
//                                    text =
//                                        "🌾 Sold in Halli Santhe",
//                                    color =
//                                        Color(
//                                            0xFF6D4C41
//                                        )
//                                )
                            }
                        }
                    }
                }
            }
        }
    }

    //---------------- Add Product Dialog ----------------
    if (showAddDialog) {

        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
            },

            title = {
                Text("Add Product")
            },

            text = {
                Column {

                    OutlinedTextField(
                        value = productName,
                        onValueChange = {
                            productName = it
                        },
                        label = {
                            Text("Product Name")
                        }
                    )

                    OutlinedTextField(
                        value = productPrice,
                        onValueChange = {
                            productPrice = it
                        },
                        label = {
                            Text("Price")
                        }
                    )

                    val categoryOptions = listOf(
                        "Vegetables",
                        "Fruits",
                        "Pulses",
                        "Grains",
                        "Dairy Products",
                        "Handmade Crafts",
                        "Electronics",
                        "Clothing",
                        "Books",
                        "Home Essentials",
                        "Livestock Products",
                        "Others"
                    )

                    var expanded by remember {
                        mutableStateOf(false)
                    }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {

                        OutlinedTextField(
                            value = productCategory,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text("Select Category")
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {

                            categoryOptions.forEach { category ->

                                DropdownMenuItem(
                                    text = {
                                        Text(category)
                                    },
                                    onClick = {
                                        productCategory = category
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = productDescription,
                        onValueChange = {
                            productDescription = it
                        },
                        label = {
                            Text("Description")
                        }
                    )

                    OutlinedTextField(
                        value = productStock,
                        onValueChange = {
                            productStock = it
                        },
                        label = {
                            Text("Stock")
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Button(
                            onClick = {
                                galleryLauncher.launch(
                                    "image/*"
                                )
                            }
                        ) {
                            Text("Choose Image")
                        }

                        Button(
                            onClick = {
                                cameraPermissionLauncher.launch(
                                    android.Manifest.permission.CAMERA
                                )
                            }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null
                            )

                            Spacer(
                                modifier =
                                    Modifier.width(5.dp)
                            )

                            Text("Camera")
                        }
                    }
                }
            },

            confirmButton = {
                Button(
                    onClick = {

                        if (
                            productName.isNotEmpty() &&
                            productPrice.isNotEmpty()
                        ) {

                            val product = Product(
                                name = productName,
                                price = productPrice,
                                category = productCategory,
                                description = productDescription,
                                stock = productStock,
                                imageUri =
                                    selectedImageUri?.toString()
                                        ?: "",
                                sellerName =
                                    SessionManager.getUsername(context)
                            )

                            productViewModel.addProduct(
                                product
                            )

                            Toast.makeText(
                                context,
                                "Product Added Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            showAddDialog = false

                            productName = ""
                            productPrice = ""
                            productCategory = ""
                            productDescription = ""
                            productStock = ""
                            selectedImageUri = null
                        }
                    }
                ) {
                    Text("Save Product")
                }
            }
        )
    }
}