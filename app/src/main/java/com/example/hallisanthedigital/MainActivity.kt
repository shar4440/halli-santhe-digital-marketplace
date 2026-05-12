package com.example.hallisanthedigital

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.hallisanthedigital.navigation.NavGraph
import com.example.hallisanthedigital.navigation.Screen
import com.example.hallisanthedigital.ui.theme.HalliSantheDigitalTheme
import com.example.hallisanthedigital.utils.SessionManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        val startDestination =
            if (SessionManager.isLoggedIn(context)) {

                if (
                    SessionManager.getRole(context) == "Seller"
                ) {
                    Screen.SellerDashboard.route
                } else {
                    Screen.BuyerDashboard.route
                }

            } else {
                Screen.Login.route
            }

        setContent {
            HalliSantheDigitalTheme {

                val navController =
                    rememberNavController()

                NavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}