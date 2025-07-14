package com.example.userhostel.home.billing.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.userhostel.R
import com.example.userhostel.home.billing.data.repository.FirestoreBillingRepository
import com.example.userhostel.home.billing.presentation.viewmodel.BillingViewModel
import kotlinx.coroutines.launch
import kotlin.String

@Composable
fun BillingScreen(
    modifier: Modifier = Modifier,
    token: String?,
    name: String?,
    hostelNo: String?,
    rollNumber: String?) {
    val viewModel = remember { BillingViewModel(FirestoreBillingRepository()) }
    val total by viewModel.totalAmount.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.fetchTotal(hostelNo.toString(), rollNumber.toString())
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = Color(0xFFFFF3E0) // light orange background
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(24.dp),

        ) {
            TotalCard(amount = total)
            ScanPayCard(icon = painterResource(id = R.drawable.qr_code))
        }
    }
}

@Composable
fun TotalCard(amount: Int) {
    Surface(
        color = Color(0xFFFFD6A5),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CURRENT BILL",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "₹$amount",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ScanPayCard(icon: Painter) {
    Surface(
        color = Color(0xFFFFB77D),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Scan and Pay",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = icon,
                contentDescription = "QR Code",
                modifier = Modifier.size(120.dp)
            )
        }
    }
}
