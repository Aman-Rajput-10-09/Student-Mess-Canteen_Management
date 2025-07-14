package com.example.userhostel.home.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userhostel.home.dashboard.presentation.state.DashboardUiState
import com.example.userhostel.home.dashboard.usecase.GetDashboardDataUseCase
import com.example.userhostel.home.history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: HistoryRepository,
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardUiState())
    val state: StateFlow<DashboardUiState> = _state

    fun loadDashboard(hostel: String,department: String, rollNumber: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val transactions = repository.getTransactionsForRoll(hostel, department, rollNumber)
            val stats = getDashboardData(transactions)

            val prices = transactions.map { it.price }.sorted()

            val average = if (prices.isNotEmpty()) prices.average().toFloat() else 0f
            val median = if (prices.isNotEmpty()) {
                val mid = prices.size / 2
                if (prices.size % 2 == 0)
                    ((prices[mid - 1] + prices[mid]) / 2f)
                else
                    prices[mid].toFloat()
            } else 0f
            val max = prices.maxOrNull() ?: 0
            val min = prices.minOrNull() ?: 0

            // Example bar data: category wise count/amount (update as needed)
            val barData = stats.dailyTotals // Reuse daily totals or define category-wise map

            _state.value = DashboardUiState(
                isLoading = false,
                totalSpent = stats.totalSpent,
                dailyData = stats.dailyTotals.toList(), // ✅ Corrected here
                pieData = stats.pieChartData,
                barData = stats.dailyTotals.toList(), // or proper barData if separate
                average = average,
                median = median,
                max = max,
                min = min
            )

        }
    }
}
