package com.example.hallisanthedigital.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")

    object Register : Screen("register")

    object SellerDashboard :
        Screen("seller_dashboard")

    object BuyerDashboard :
        Screen("buyer_dashboard")

    object ProductDetail :
        Screen("product_detail")

    object Negotiation :
        Screen("negotiation")

    object Profile : Screen("profile")
}