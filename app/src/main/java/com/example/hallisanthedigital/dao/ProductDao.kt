package com.example.hallisanthedigital.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hallisanthedigital.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query(
        "SELECT * FROM products WHERE id=:productId"
    )
    suspend fun getProductById(
        productId: Int
    ): Product
}