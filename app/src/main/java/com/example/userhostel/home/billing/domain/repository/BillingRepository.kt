package com.example.userhostel.home.billing.domain.repository

interface BillingRepository {
    suspend fun getTotalAmount(hostel: String, rollNumber: String): Int
}
