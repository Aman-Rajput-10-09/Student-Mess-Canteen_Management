package com.example.userhostel.signup.presentation.screens

import com.example.userhostel.R
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.userhostel.signup.presentation.state.SignupUiState
import com.example.userhostel.signup.viewmodel.SignupViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun OtpVerificationScreen(
    uiState: SignupUiState,
    viewModel: SignupViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        val credential = Identity.getSignInClient(context)
            .getSignInCredentialFromIntent(result.data)
        val idToken = credential.googleIdToken
        val email = credential.id // This gives the signed-in email

        if (idToken != null) {
            viewModel.signInWithGoogle(idToken)
            viewModel.updateEmail(email) // ← Store email in SignupData
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFA726)) // Orange background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, start = 24.dp, end = 24.dp, bottom = 60.dp)
                .background(Color.White, shape = RoundedCornerShape(32.dp))
        ) {

            // Header with back button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C42)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("← Back", color = Color.White)
                }
                Text(
                    text = "Google Sign-In",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Sign in using your Google account to continue",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val request = BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(
                            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId("702283056049-84cmvd97b7fmispvkrh3bp5aut8a6rhm.apps.googleusercontent.com")
                                .setFilterByAuthorizedAccounts(false)
                                .build()
                        )
                        .setAutoSelectEnabled(true)
                        .build()

                    Identity.getSignInClient(context).beginSignIn(request)
                        .addOnSuccessListener { result ->
                            val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                            launcher.launch(intentSenderRequest)
                        }
                        .addOnFailureListener {
                            // Optional: Show error message to user
                        }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C42)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Google Sign-In",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sign in with Google", color = Color.Black)
            }
        }
    }
}
