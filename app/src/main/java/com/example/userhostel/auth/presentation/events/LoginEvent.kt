package com.example.userhostel.auth.presentation.events

sealed class LoginEvent {
    data class OnPhoneChange(val phone: String) : LoginEvent()
    data class OnOtpChange(val otp: String) : LoginEvent()
    object OnSendOtp : LoginEvent()
    object OnLogin : LoginEvent()
}
