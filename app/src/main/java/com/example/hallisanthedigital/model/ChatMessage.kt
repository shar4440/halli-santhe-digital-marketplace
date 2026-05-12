package com.example.hallisanthedigital.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessage(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val productId: Int,

    val senderName: String,

    val message: String,

    val timestamp: Long =
        System.currentTimeMillis()
)