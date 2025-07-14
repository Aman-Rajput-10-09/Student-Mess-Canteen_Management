package com.example.userhostel.signup.domain.repository

import com.example.userhostel.signup.domain.model.SignupData

interface SignupRepository {
    suspend fun uploadDocument(file: ByteArray, type: String): String
    suspend fun submitSignupData(data: SignupData)
    suspend fun signInWithCredential(idToken: String): Result<Unit>
    suspend fun saveSignupData(data: SignupData, uid: String): Result<Unit>
    suspend fun checkUserExists(email: String, rollNumber: String, phoneNumber: String, hostelNo: String): Boolean


}
