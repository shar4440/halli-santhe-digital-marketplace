package com.example.hallisanthedigital.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hallisanthedigital.model.User
import com.example.hallisanthedigital.repository.AppRepository

class AuthViewModel(
    private val repository: AppRepository
) : ViewModel() {

    // Register user safely
    suspend fun registerUser(
        username: String,
        password: String,
        mobile: String,
        address: String,
        role: String
    ): Boolean {

        // Check if username already exists
        val existingUser =
            repository.getUserByUsername(
                username
            )

        if (existingUser != null) {
            return false
        }

        val user = User(
            username = username,
            password = password,
            mobile = mobile,
            address = address,
            role = role
        )

        repository.registerUser(user)

        return true
    }

    // Login user
    suspend fun loginUser(
        username: String,
        password: String
    ): User? {

        return repository.loginUser(
            username,
            password
        )
    }
}