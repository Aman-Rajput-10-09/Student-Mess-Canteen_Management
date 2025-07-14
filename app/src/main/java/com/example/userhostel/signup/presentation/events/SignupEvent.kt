package com.example.userhostel.signup.presentation.events

import com.example.userhostel.signup.domain.model.SignupData

sealed class SignupEvent {
    // Step 1: Form data entry
    data class OnDataChange(val data: SignupData) : SignupEvent()

    // Step 3: Document upload
    data class UploadHostelId(val fileName: String) : SignupEvent()
    data class UploadHmsId(val fileName: String) : SignupEvent()
    data class OnUploadDocument(val byteArray: ByteArray, val type: String) : SignupEvent()
    object OnUploadDocuments : SignupEvent()

    // Final submit
    object OnSubmit : SignupEvent()

    // Navigation
    object NextStep : SignupEvent()
    object GoBack : SignupEvent()
}
