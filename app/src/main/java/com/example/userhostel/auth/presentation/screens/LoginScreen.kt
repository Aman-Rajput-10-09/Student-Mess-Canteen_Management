package com.example.userhostel.auth.presentation.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.userhostel.R
import com.example.userhostel.auth.presentation.navigation.Screen
import com.example.userhostel.auth.presentation.viewmodel.LoginViewModel
import com.example.userhostel.home.HomeActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Launcher for Google One Tap Sign-In
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        val credential = Identity.getSignInClient(context)
            .getSignInCredentialFromIntent(result.data)
        val idToken = credential.googleIdToken
        if (idToken != null) {
            viewModel.signInWithGoogle(idToken, context)

        } else {
            Toast.makeText(context, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
        }
    }

    // Navigate to HomeActivity after successful login
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn && uiState.token != null) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra("token", uiState.token)
                putExtra("name", uiState.name)
                putExtra("hostelNo", uiState.hostelNo)
                putExtra("rollNumber", uiState.rollNumber)
            }
            context.startActivity(intent)

        }
    }

    // --- UI ---
    Box(modifier = Modifier.fillMaxSize()) {

        // Header section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color(0xFFFDF7F0))
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE07C3D)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("← Back", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "User Avatar",
                tint = Color.Black,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFDF7F0))
                    .padding(8.dp)
                    .padding(bottom = 8.dp)
            )
        }

        // Orange login card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp)
                .background(
                    color = Color(0xFFFF8C42),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {
                        val request = BeginSignInRequest.builder()
                            .setGoogleIdTokenRequestOptions(
                                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                    .setSupported(true)
                                    .setServerClientId(R.string.default_web_id)
                                    .setFilterByAuthorizedAccounts(true)
                                    .build()
                            )
                            .setAutoSelectEnabled(true)
                            .build()

                        Identity.getSignInClient(context).beginSignIn(request)
                            .addOnSuccessListener { result ->
                                val intentSenderRequest =
                                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                                launcher.launch(intentSenderRequest)
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Account not registered", Toast.LENGTH_SHORT).show()
                            }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Google Sign-In",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Login with Google", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate(Screen.Signup.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA65C))
                ) {
                    Text("Create Account", color = Color.White)
                }
            }
        }
    }
}
