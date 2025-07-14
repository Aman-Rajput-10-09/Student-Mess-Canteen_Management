package com.example.userhostel.auth.presentation.navigation

// ui.navigation.Screen.kt

sealed class Screen(val route: String) {
    object First : Screen("first_screen")
    object Login : Screen("login_screen")
    object Signup : Screen("signup_screen")
}
