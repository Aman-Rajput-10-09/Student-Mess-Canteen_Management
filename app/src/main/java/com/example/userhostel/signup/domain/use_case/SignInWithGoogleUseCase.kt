package com.example.userhostel.signup.domain.use_case

// domain/usecase/SignInWithGoogleUseCase.kt
interface SignInWithGoogleUseCase {
    suspend fun signInWithCredential(idToken: String): Result<Unit>
}
