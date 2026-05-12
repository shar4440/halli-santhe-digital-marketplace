package com.example.hallisanthedigital.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hallisanthedigital.ui.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // Login
        composable(
            Screen.Login.route
        ) {
            LoginScreen(navController)
        }

        // Register
        composable(
            Screen.Register.route
        ) {
            RegisterScreen(navController)
        }

        // Seller Dashboard
        composable(
            Screen.SellerDashboard.route
        ) {
            SellerDashboardScreen(navController)
        }

        // Buyer Dashboard
        composable(
            Screen.BuyerDashboard.route
        ) {
            BuyerDashboardScreen(navController)
        }

        // Product Detail with productId
        composable(
            route =
                "${Screen.ProductDetail.route}/{productId}"
        ) { backStackEntry ->

            ProductDetailScreen(
                navController,
                backStackEntry
            )
        }

        // Negotiation with productId
        composable(
            route =
                "${Screen.Negotiation.route}/{productId}"
        ) { backStackEntry ->

            NegotiationScreen(
                navController,
                backStackEntry
            )
        }


        composable(
            Screen.Profile.route
        ) {
            ProfileScreen(navController)
        }
    }
}