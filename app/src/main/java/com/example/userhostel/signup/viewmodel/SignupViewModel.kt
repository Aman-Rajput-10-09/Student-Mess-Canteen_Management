package com.example.userhostel.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userhostel.signup.data.repository.SignupRepositoryImpl
import com.example.userhostel.signup.domain.repository.SignupRepository
import com.example.userhostel.signup.presentation.events.SignupEvent
import com.example.userhostel.signup.presentation.state.SignupUiState
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.withTimeoutOrNull

class SignupViewModel(
    private val repository: SignupRepository = SignupRepositoryImpl(),
) : ViewModel() {

    var uiState by mutableStateOf(SignupUiState())
        private set

    private val auth = FirebaseAuth.getInstance()

    init {
        autoFillEmail()
    }

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnDataChange -> {
                uiState = uiState.copy(data = event.data)
            }

            is SignupEvent.UploadHostelId -> {
                uiState = uiState.copy(hostelIdFileName = event.fileName)
                checkDocumentsUploaded()
            }

            is SignupEvent.UploadHmsId -> {
                uiState = uiState.copy(hmsIdFileName = event.fileName)
                checkDocumentsUploaded()
            }

            SignupEvent.OnUploadDocuments -> {
                uiState = uiState.copy(isDocumentUploadValid = true)
                nextStep()
            }

            SignupEvent.NextStep -> nextStep()

            SignupEvent.OnSubmit -> {
                // Handle final submission
            }

            is SignupEvent.OnUploadDocument -> {
                val parts = event.type.split(":")
                val type = parts.getOrNull(0)
                val fileName = parts.getOrNull(1)

                if (type != null && fileName != null) {
                    when (type) {
                        "hostel" -> uiState = uiState.copy(hostelIdFileName = fileName)
                        "hms" -> uiState = uiState.copy(hmsIdFileName = fileName)
                    }
                    checkDocumentsUploaded()
                }
            }

            SignupEvent.GoBack -> goBack()
        }
    }

    private fun nextStep() {
        if (uiState.isAuthenticated) {
            val currentUser = auth.currentUser
            val userId = currentUser?.uid

            if (userId != null) {
                viewModelScope.launch {
                    uiState = uiState.copy(isLoading = true)

                    // 🔒 Check if user already exists
                    val exists = withTimeoutOrNull(3000L) {
                        repository.checkUserExists(
                            email = uiState.data.email,
                            rollNumber = uiState.data.rollNumber,
                            phoneNumber = uiState.data.phoneNumber,
                            hostelNo = uiState.data.hostelNo
                        )
                    }

                    if (exists == true) {
                        uiState = uiState.copy(
                            isLoading = false,
                            authError = "⚠️ Account already exists with this email or roll number"
                        )
                        return@launch // ⛔ Do not proceed further
                    }

                    val result = repository.saveSignupData(uiState.data, userId)

                    uiState = if (result.isSuccess) {
                        uiState.copy(
                            isLoading = false,
                            currentStep = uiState.currentStep + 1
                        )
                    } else {
                        uiState.copy(
                            isLoading = false,
                            authError = result.exceptionOrNull()?.message ?: "Data save failed"
                        )
                    }
                }
            }
        } else {
            uiState = uiState.copy(currentStep = uiState.currentStep + 1)
        }
    }

    private fun goBack() {
        if (uiState.currentStep > 1) {
            uiState = uiState.copy(currentStep = uiState.currentStep - 1)
        }
    }

    private fun checkDocumentsUploaded() {
        val isValid = !uiState.hostelIdFileName.isNullOrEmpty() &&
                !uiState.hmsIdFileName.isNullOrEmpty()

        uiState = uiState.copy(isDocumentUploadValid = isValid)
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val result = repository.signInWithCredential(idToken)
            result.onSuccess {
                uiState = uiState.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    currentStep = uiState.currentStep + 1
                )
            }.onFailure {
                uiState = uiState.copy(
                    isLoading = false,
                    authError = it.message ?: "Unknown error"
                )
            }
        }
    }

    fun updateEmail(email: String) {
        uiState = uiState.copy(
            data = uiState.data.copy(email = email)
        )
    }

    private fun autoFillEmail() {
        auth.currentUser?.email?.let { email ->
            uiState = uiState.copy(
                data = uiState.data.copy(email = email)
            )
        }
    }
}
