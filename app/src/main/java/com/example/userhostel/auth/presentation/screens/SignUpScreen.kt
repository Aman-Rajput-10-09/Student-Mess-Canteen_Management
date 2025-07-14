package com.example.userhostel.auth.presentation.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.userhostel.signup.presentation.screens.CreateAccountScreen
import com.example.userhostel.signup.presentation.screens.OtpVerificationScreen
import com.example.userhostel.signup.presentation.screens.SignupSuccessScreen
import com.example.userhostel.signup.presentation.screens.UploadDocumentsScreen
import com.example.userhostel.signup.viewmodel.SignupViewModel

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = viewModel()
)
{
    val uiState = viewModel.uiState

    when (uiState.currentStep) {
        1 -> OtpVerificationScreen(uiState, viewModel, navController)
        2 -> CreateAccountScreen(uiState, viewModel, navController)
        3 -> UploadDocumentsScreen(uiState, viewModel)
        4 -> SignupSuccessScreen(navController)
    }
}

