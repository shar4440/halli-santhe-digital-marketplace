package com.example.hallisanthedigital.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val price: String,

    val category: String,

    val description: String,

    val imageUri: String,

    val stock: String,

    val sellerName: String
)