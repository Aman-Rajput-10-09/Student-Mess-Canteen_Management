package com.example.userhostel.home.dashboard.usecase

import com.example.userhostel.home.history.domain.model.Transaction

data class DashboardStats(
    val totalSpent: Int,
    val dailyTotals: Map<String, Int>, // "2025-07-08" to amount
    val pieChartData: Map<String, Int> // can be categories/time of day/etc.
)

class GetDashboardDataUseCase {
    operator fun invoke(transactions: List<Transaction>): DashboardStats {
        val total = transactions.sumOf { it.price }

        val daily = transactions.groupBy { it.date }
            .mapValues { entry -> entry.value.sumOf { it.price } }

        val timeGroups = transactions.groupBy {
            when (it.time.substringBefore(":").toInt()) {
                in 5..11 -> "Morning"
                in 12..16 -> "Afternoon"
                in 17..20 -> "Evening"
                else -> "Night"
            }
        }.mapValues { entry -> entry.value.sumOf { it.price } }

        return DashboardStats(total, daily, timeGroups)
    }
}
