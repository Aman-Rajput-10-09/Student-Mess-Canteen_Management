package com.example.userhostel.home.dashboard.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userhostel.home.dashboard.presentation.viewmodel.DashboardViewModel
import com.example.userhostel.home.dashboard.presentation.viewmodel.DashboardViewModelFactory
import com.example.userhostel.home.dashboard.usecase.GetDashboardDataUseCase
import com.example.userhostel.home.history.data.repository.FirestoreHistoryRepository

@Composable
fun DashboardEntryPoint(
    modifier: Modifier = Modifier,
    token: String?,
    name: String?,
    hostelNo: String?,
    rollNumber: String?
) {
    val historyRepository = remember { FirestoreHistoryRepository() }
    val getDashboardData = remember { GetDashboardDataUseCase() }
    val factory = remember { DashboardViewModelFactory(historyRepository, getDashboardData) }
    val viewModel: DashboardViewModel = viewModel(factory = factory)

    DashboardScreen(
        modifier = modifier, // ✅ passes Scaffold padding here
        token = token,
        name = name,
        hostelNo = hostelNo,
        rollNumber = rollNumber,
        viewModel = viewModel
    )
}
