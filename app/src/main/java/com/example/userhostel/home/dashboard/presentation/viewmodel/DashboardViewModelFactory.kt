package com.example.userhostel.home.dashboard.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userhostel.home.dashboard.usecase.GetDashboardDataUseCase
import com.example.userhostel.home.history.domain.repository.HistoryRepository

class DashboardViewModelFactory(
    private val repository: HistoryRepository,
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository, getDashboardData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
