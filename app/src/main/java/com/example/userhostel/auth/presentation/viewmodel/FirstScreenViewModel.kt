package com.example.userhostel.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirstScreenViewModel : ViewModel() {
    private val _rotationAngle = MutableStateFlow(0f)
    val rotationAngle: StateFlow<Float> = _rotationAngle

    init {
        startRotation()
    }

    private fun startRotation() {
        viewModelScope.launch {
            while (true) {
                delay(16L) // ~60fps
                _rotationAngle.value = (_rotationAngle.value + 1f) % 360f
            }
        }
    }
}
