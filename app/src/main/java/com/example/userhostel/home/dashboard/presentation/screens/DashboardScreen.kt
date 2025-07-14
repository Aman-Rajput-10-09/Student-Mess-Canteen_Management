package com.example.userhostel.home.dashboard.presentation.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.userhostel.home.dashboard.components.BarChart
import com.example.userhostel.home.dashboard.components.LineChart
import com.example.userhostel.home.dashboard.components.PieChart
import com.example.userhostel.home.dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    token: String?,
    name: String?,
    hostelNo: String?,
    rollNumber: String?,
    viewModel: DashboardViewModel
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var selectedDepartment by remember { mutableStateOf("mess") }

    // Reload when any key changes
    LaunchedEffect(hostelNo, rollNumber, selectedDepartment) {
        if (!hostelNo.isNullOrEmpty() && !rollNumber.isNullOrEmpty()) {
            viewModel.loadDashboard(hostelNo, selectedDepartment, rollNumber)
        }
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 💡 Department Selector
        Text("Select Department", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedDepartment == "mess",
                onClick = { selectedDepartment = "mess" }
            )
            Text("Mess", modifier = Modifier.padding(end = 16.dp))

            RadioButton(
                selected = selectedDepartment == "canteen",
                onClick = { selectedDepartment = "canteen" }
            )
            Text("Canteen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Text("Welcome, $name", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total Spent", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("₹${state.totalSpent}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ChartCard(title = "Daily Spend (Line Chart)") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LineChart(data = state.dailyData.toMap())
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ChartCard(title = "Time-of-Day Spend (Pie Chart)") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    PieChart(data = state.pieData)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ChartCard(title = "Daily Stats") {
                Text("Average Spend: ₹${state.average}")
                Text("Median Spend: ₹${state.median}")
                Text("Max Spend: ₹${state.max}")
                Text("Min Spend: ₹${state.min}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            ChartCard(title = "Category-wise Spend (Bar Chart)") {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    BarChart(data = state.barData)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ChartCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
