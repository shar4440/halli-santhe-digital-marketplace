package com.example.hallisanthedigital.utils

import android.content.Context

object SessionManager {

    private const val PREF_NAME = "halli_santhe_session"

    private const val KEY_USERNAME = "username"
    private const val KEY_ROLE = "role"
    private const val KEY_MOBILE = "mobile"
    private const val KEY_ADDRESS = "address"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    //-----------------------------------------
    // Save Login Session
    //-----------------------------------------
    fun saveUserSession(
        context: Context,
        username: String,
        role: String,
        mobile: String,
        address: String
    ) {
        val sharedPreferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val editor = sharedPreferences.edit()

        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_ROLE, role)
        editor.putString(KEY_MOBILE, mobile)
        editor.putString(KEY_ADDRESS, address)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)

        editor.apply()
    }

    //-----------------------------------------
    // Check Login Status
    //-----------------------------------------
    fun isLoggedIn(
        context: Context
    ): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        return sharedPreferences.getBoolean(
            KEY_IS_LOGGED_IN,
            false
        )
    }

    //-----------------------------------------
    // Get Username
    //-----------------------------------------
    fun getUsername(
        context: Context
    ): String {
        val sharedPreferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        return sharedPreferences.getString(
            KEY_USERNAME,
            ""
        ) ?: ""
    }

    //-----------------------------------------
    // Get Role
    //-----------------------------------------
    fun getRole(
        context: Context
    ): String {
        val sharedPreferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        return sharedPreferences.getString(
            KEY_ROLE,
            ""
        ) ?: ""
    }

    //-----------------------------------------
    // Get Mobile
    //-----------------------------------------
    fun getMobile(
        context: Context
    ): String {
        val sharedPreferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        return sharedPreferences.getString(
            KEY_MOBILE,
            ""
        ) ?: ""
    }

    //-----------------------------------------
    // Get Address
    //-----------------------------------------
    fun getAddress(
        context: Context
    ): String {
        val sharedPreferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        return sharedPreferences.getString(
            KEY_ADDRESS,
            ""
        ) ?: ""
    }

    //-----------------------------------------
    // Logout
    //-----------------------------------------
    fun logout(
        context: Context
    ) {
        val sharedPreferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        sharedPreferences.edit()
            .clear()
            .apply()
    }
}