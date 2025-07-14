package com.example.userhostel.signup.domain.model

data class SignupData(
    val name: String = "",
    val email: String = "",
    val hostelNo: String = "",
    val roomNo: String = "",
    val phoneNumber: String = "",
    val hmsId: String = "",
    val rollNumber: String = "",
    val hostelIdUrl: String? = null,
    val hmsIdUrl: String? = null,
)
