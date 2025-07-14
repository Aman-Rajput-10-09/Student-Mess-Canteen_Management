package com.example.userhostel.home.billing.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.userhostel.home.billing.domain.repository.BillingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BillingViewModel(private val repository: BillingRepository) : ViewModel() {

    private val _totalAmount = MutableStateFlow(0)
    val totalAmount: StateFlow<Int> = _totalAmount

    suspend fun fetchTotal(hostel: String, rollNumber: String) {
        _totalAmount.value = repository.getTotalAmount(hostel, rollNumber)
    }
}
