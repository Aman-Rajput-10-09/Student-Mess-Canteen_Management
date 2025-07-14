package com.example.userhostel.home.history.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userhostel.home.history.domain.model.Transaction
import com.example.userhostel.home.history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: HistoryRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    fun loadHistory(hostel: String, department: String, rollNumber: String) {
        viewModelScope.launch {
            val result = repository.getTransactionsForRoll(hostel, department, rollNumber)
            _transactions.value = result
        }
    }
}
