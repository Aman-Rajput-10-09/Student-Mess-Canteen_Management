package com.example.userhostel.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.userhostel.MainActivity
import com.example.userhostel.auth.data.SessionManager
import com.example.userhostel.home.presentation.HomeScreen
import com.example.userhostel.ui.theme.UserHostelTheme
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : ComponentActivity() {

    private fun logout(context: Context) {
        SessionManager(context).logout()
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
        finish() // Optional, to finish HomeActivity too
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = intent.getStringExtra("token")
        val name = intent.getStringExtra("name")
        val hostelNo = intent.getStringExtra("hostelNo")
        val rollNumber = intent.getStringExtra("rollNumber")

        Toast.makeText(this, "Logged in with token:\n$token", Toast.LENGTH_LONG).show()

        setContent {
            UserHostelTheme {
                HomeScreen(
                    token = token,
                    name = name,
                    hostelNo = hostelNo,
                    rollNumber = rollNumber,
                    onLogout = { logout(this) } // Pass logout callback
                )
            }
        }
    }
}
