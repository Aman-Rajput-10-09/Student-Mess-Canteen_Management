package com.example.userhostel.home.dashboard.presentation.state

data class DashboardUiState(
    val isLoading: Boolean = true,
    val totalSpent: Int = 0,
    val dailyData: List<Pair<String, Int>> = emptyList(),
    val pieData: Map<String, Int> = emptyMap(),
    val barData: List<Pair<String, Int>> = emptyList(),
    val average: Float = 0f,
    val median: Float = 0f,
    val max: Int = 0,
    val min: Int = 0
)
