package com.example.userhostel.auth.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userhostel.auth.data.SessionManager
import com.example.userhostel.auth.presentation.state.LoginUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun signInWithGoogle(idToken: String, context: Context) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val sessionManager = SessionManager(context)

        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        val user = task.result.user
                        val uid = user?.uid

                        if (uid != null) {
                            try {
                                val snapshot = firestore.collection("users").document(uid).get().await()

                                val name = snapshot.getString("name") ?: ""
                                val hostelNo = snapshot.getString("hostelNo") ?: ""
                                val rollNumber = snapshot.getString("rollNumber") ?: ""

                                // Save session
                                sessionManager.saveUserSession(name, hostelNo, rollNumber, idToken)

                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    isLoggedIn = true,
                                    token = idToken,
                                    name = name,
                                    hostelNo = hostelNo,
                                    rollNumber = rollNumber
                                )
                            } catch (e: Exception) {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    error = e.localizedMessage ?: "Failed to fetch user data"
                                )
                            }
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = task.exception?.localizedMessage ?: "Login failed"
                        )
                    }
                }
            }
    }

    fun resetError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
