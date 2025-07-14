package com.example.userhostel.home.history.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.userhostel.home.history.data.repository.FirestoreHistoryRepository
import com.example.userhostel.home.history.presentation.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    token: String?,
    name: String?,
    hostelNo: String?,
    rollNumber: String?
) {
    val viewModel = remember { HistoryViewModel(FirestoreHistoryRepository()) }
    val transactions by viewModel.transactions.collectAsState()
    var selectedDepartment by remember { mutableStateOf("mess") }

    LaunchedEffect(hostelNo, rollNumber, selectedDepartment) {
        if (!hostelNo.isNullOrEmpty() && !rollNumber.isNullOrEmpty()) {
            viewModel.loadHistory(hostelNo, selectedDepartment, rollNumber)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0D6))
            .padding(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF0D6))
        ) {
            Text(
                "Payment History",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // 🔁 Department Selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF0D6)),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val groupedTransactions = transactions
                    .groupBy { it.date }
                    .toSortedMap(compareByDescending { it })

                groupedTransactions.forEach { (date, txnList) ->
                    item {
                        Text(
                            text = date,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                    }

                    items(txnList) { txn ->
                        Surface(
                            color = Color(0xFFFFD6A5),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Roll No: $rollNumber", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("₹${txn.price} at ${txn.time}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                    }
                }
            }

            if (transactions.isEmpty()) {
                Text("No transactions found.", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
