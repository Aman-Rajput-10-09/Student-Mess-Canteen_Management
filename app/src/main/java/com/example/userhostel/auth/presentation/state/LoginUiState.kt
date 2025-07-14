package com.example.userhostel.auth.presentation.state

data class LoginUiState(
    val phone: String = "",
    val otp: String = "",
    val isOtpSent: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val token: String? = null,
    val isLoggedIn: Boolean = false,
    val name: String = "",
    val hostelNo: String = "",
    val rollNumber: String = ""
)
