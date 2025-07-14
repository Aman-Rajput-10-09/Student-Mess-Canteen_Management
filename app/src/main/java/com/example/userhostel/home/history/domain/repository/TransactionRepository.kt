package com.example.userhostel.home.history.domain.repository

import com.example.userhostel.home.history.domain.model.Transaction

interface HistoryRepository {
    suspend fun getTransactionsForRoll(hostel: String, department: String, rollNumber: String): List<Transaction>
}
