package com.example.userhostel.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Billing : BottomNavItem("Billing", Icons.Default.ShoppingCart)
    object Dashboard : BottomNavItem("Dashboard", Icons.Default.Home)
    object History : BottomNavItem("History", Icons.Default.AccountCircle)
}
