package com.example.hallisanthedigital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hallisanthedigital.repository.AppRepository

class AppViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                AuthViewModel::class.java
            )
        ) {
            return AuthViewModel(
                repository
            ) as T
        }

        if (
            modelClass.isAssignableFrom(
                ProductViewModel::class.java
            )
        ) {
            return ProductViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel"
        )
    }
}