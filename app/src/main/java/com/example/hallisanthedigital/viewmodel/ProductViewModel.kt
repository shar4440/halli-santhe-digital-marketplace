package com.example.hallisanthedigital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hallisanthedigital.model.ChatMessage
import com.example.hallisanthedigital.model.Product
import com.example.hallisanthedigital.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: AppRepository
) : ViewModel() {

    // Product list
    private val _products =
        MutableStateFlow<List<Product>>(emptyList())

    val products: StateFlow<List<Product>> =
        _products

    // Single product
    private val _selectedProduct =
        MutableStateFlow<Product?>(null)

    val selectedProduct:
            StateFlow<Product?> =
        _selectedProduct

    // Chat messages
    private val _chatMessages =
        MutableStateFlow<List<ChatMessage>>(
            emptyList()
        )

    val chatMessages:
            StateFlow<List<ChatMessage>> =
        _chatMessages

    private val _messageCounts =
        MutableStateFlow<Map<Int, Int>>(emptyMap())

    val messageCounts: StateFlow<Map<Int, Int>> =
        _messageCounts

    // Add Product
    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.addProduct(product)
        }
    }

    // Load All Products
    fun loadProducts() {
        viewModelScope.launch {
            repository.getAllProducts().collect {
                _products.value = it
            }
        }
    }

    // Product Detail
    fun getProductById(
        productId: Int
    ) {
        viewModelScope.launch {
            _selectedProduct.value =
                repository.getProductById(
                    productId
                )
        }
    }

    // Send Chat Message
    fun sendMessage(
        chatMessage: ChatMessage
    ) {
        viewModelScope.launch {
            repository.sendMessage(
                chatMessage
            )
        }
    }

    // Load chat messages
    fun loadChatMessages(
        productId: Int
    ) {
        viewModelScope.launch {
            repository.getMessagesByProduct(
                productId
            ).collect {
                _chatMessages.value = it
            }
        }
    }


    fun loadMessageCountForProducts(
        products: List<Product>
    ) {
        viewModelScope.launch {

            val countsMap = mutableMapOf<Int, Int>()

            products.forEach { product ->

                val count =repository.getUniqueBuyerCountForProduct(
                    product.id,
                    product.sellerName
                )

                countsMap[product.id] = count
            }

            _messageCounts.value = countsMap
        }
    }
}