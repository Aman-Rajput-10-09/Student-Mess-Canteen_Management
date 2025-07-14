package com.example.userhostel.signup.data.repository

import com.example.userhostel.signup.domain.model.SignupData
import com.example.userhostel.signup.domain.repository.SignupRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SignupRepositoryImpl : SignupRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun uploadDocument(file: ByteArray, type: String): String = "https://fake-url.com/$type"
    override suspend fun submitSignupData(data: SignupData) {
        // Firestore saving logic
    }

    override suspend fun signInWithCredential(idToken: String): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await() // use kotlinx-coroutines-play-services
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveSignupData(data: SignupData, uid: String): Result<Unit> {
        return try {
            firestore.collection("users").document(uid).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkUserExists(email: String, rollNumber: String, phoneNumber: String, hostelNo: String): Boolean {
        return try {
            val usersCollection = firestore.collection("users")

            val emailQuery = usersCollection
                .whereEqualTo("email", email)
                .get()
                .await()

            val phoneQuery = usersCollection
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .await()

            // rollNumber must be unique *within* the same hostel
            val rollHostelQuery = usersCollection
                .whereEqualTo("hostelNo", hostelNo)
                .whereEqualTo("rollNumber", rollNumber)
                .get()
                .await()

            return emailQuery.isEmpty.not() ||
                    phoneQuery.isEmpty.not() ||
                    rollHostelQuery.isEmpty.not()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }



}
