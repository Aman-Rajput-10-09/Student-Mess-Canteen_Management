package com.example.userhostel.signup.presentation.state

import com.example.userhostel.signup.domain.model.SignupData

data class SignupUiState(
    val data: SignupData = SignupData(),
    val currentStep: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null,
    val hostelIdFileName: String? = null,
    val hmsIdFileName: String? = null,
    val isDocumentUploadValid: Boolean = false,
    val isAuthenticated: Boolean = false,
    val authError: String? = null,
    )
