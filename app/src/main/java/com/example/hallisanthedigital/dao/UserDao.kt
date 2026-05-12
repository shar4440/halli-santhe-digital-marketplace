package com.example.hallisanthedigital.dao

import androidx.room.*
import com.example.hallisanthedigital.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun register(user: User): Long

    @Query(
        "SELECT * FROM users WHERE username = :username LIMIT 1"
    )
    suspend fun getUserByUsername(
        username: String
    ): User?

    @Query(
        "SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1"
    )
    suspend fun login(
        username: String,
        password: String
    ): User?
}