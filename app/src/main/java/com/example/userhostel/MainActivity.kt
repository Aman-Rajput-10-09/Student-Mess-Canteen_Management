package com.example.userhostel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.userhostel.auth.data.SessionManager
import com.example.userhostel.auth.presentation.navigation.Screen
import com.example.userhostel.auth.presentation.screens.FirstScreen
import com.example.userhostel.auth.presentation.screens.LoginScreen
import com.example.userhostel.auth.presentation.screens.SignupScreen
import com.example.userhostel.home.HomeActivity
import com.example.userhostel.ui.theme.UserHostelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sessionManager = SessionManager(this)

        // Auto-redirect to Home if user is already logged in
        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("token", sessionManager.getToken())
                putExtra("name", sessionManager.getName())
                putExtra("hostelNo", sessionManager.getHostelNo())
                putExtra("rollNumber", sessionManager.getRollNumber())
            }
            startActivity(intent)
            finish()
            return
        }

        // If not logged in, show auth flow
        setContent {
            UserHostelTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.First.route) {
                    composable(Screen.First.route) { FirstScreen(navController) }
                    composable(Screen.Login.route) { LoginScreen(navController) }
                    composable(Screen.Signup.route) { SignupScreen(navController) }
                }
            }
        }
    }
}
