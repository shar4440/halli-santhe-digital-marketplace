package com.example.hallisanthedigital.repository

import com.example.hallisanthedigital.dao.*
import com.example.hallisanthedigital.model.*
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val userDao: UserDao,
    private val productDao: ProductDao,
    private val chatDao: ChatDao
) {

    suspend fun registerUser(user: User) =
        userDao.register(user)

    suspend fun loginUser(
        username: String,
        password: String
    ) = userDao.login(username, password)

    suspend fun addProduct(product: Product) =
        productDao.insertProduct(product)

    fun getAllProducts(): Flow<List<Product>> =
        productDao.getAllProducts()

    suspend fun getProductById(
        productId: Int
    ) = productDao.getProductById(productId)

    suspend fun sendMessage(
        chatMessage: ChatMessage
    ) = chatDao.sendMessage(chatMessage)

    fun getMessagesByProduct(
        productId: Int
    ) = chatDao.getMessagesByProduct(productId)

    suspend fun getUserByUsername(
        username: String
    ) = userDao.getUserByUsername(username)

    suspend fun getUniqueBuyerCountForProduct(
        productId: Int,
        sellerName: String
    ): Int {
        return chatDao.getUniqueBuyerCountForProduct(
            productId,
            sellerName
        )
    }
}