package com.example.hallisanthedigital.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hallisanthedigital.model.ChatMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert
    suspend fun sendMessage(
        message: ChatMessage
    )

    @Query("""
        SELECT * FROM chat_messages
        WHERE productId=:productId
        ORDER BY timestamp ASC
    """)
    fun getMessagesByProduct(
        productId: Int
    ): Flow<List<ChatMessage>>

    @Query("""
SELECT COUNT(DISTINCT senderName)
FROM chat_messages
WHERE productId = :productId
AND senderName != :sellerName
""")
    suspend fun getUniqueBuyerCountForProduct(
        productId: Int,
        sellerName: String
    ): Int
}
