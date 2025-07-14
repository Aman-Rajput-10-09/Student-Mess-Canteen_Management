package com.example.userhostel.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.userhostel.R
import com.example.userhostel.home.billing.presentation.screens.BillingScreen
import com.example.userhostel.home.dashboard.presentation.screens.DashboardEntryPoint
import com.example.userhostel.home.dashboard.presentation.screens.DashboardScreen
import com.example.userhostel.home.history.presentation.screens.HistoryScreen
import com.example.userhostel.home.navigation.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    token: String?,
    name: String?,
    hostelNo: String?,
    rollNumber: String?,
    onLogout: () -> Unit
) {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Dashboard) }

    Scaffold(
        containerColor = Color(0xFFFFF3E0), // Background matches Billing/History
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User Avatar
                        Icon(
                            painter = painterResource(id = R.drawable.ic_user),
                            contentDescription = "User Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = Color(0xFF7D57C1),
                                    shape = CircleShape
                                )
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        // Name and Hostel
                        Column {
                            Text(
                                text = "Boys Hostel: $hostelNo",
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Roll No.: $rollNumber",
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                        }
                    }
                },
                actions = @androidx.compose.runtime.Composable {
                    var expanded by remember { mutableStateOf(false) }

                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                expanded = false
                                onLogout() // <- Trigger logout
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD9CBB8), // Beige background
                    titleContentColor = Color.Black
                )
            )
        }
        ,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFE07C3D), // Match app bar
                contentColor = Color.Black
            ) {
                val items = listOf(
                    BottomNavItem.Billing,
                    BottomNavItem.Dashboard,
                    BottomNavItem.History
                )
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(item.icon, contentDescription = item.title, tint = Color.Black)
                        },
                        label = {
                            Text(item.title, color = Color.Black)
                        },
                        selected = selectedItem == item,
                        onClick = { selectedItem = item },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            indicatorColor = Color(0xFFFFF0D6), // Orange indicator
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFFFFF3E0)) // Ensure inner screens match too

        when (selectedItem) {
            is BottomNavItem.Billing -> BillingScreen(
                modifier = Modifier.padding(innerPadding),
                token = token,
                name = name,
                hostelNo = hostelNo,
                rollNumber = rollNumber
            )
            is BottomNavItem.Dashboard -> DashboardEntryPoint(
                modifier = Modifier.padding(innerPadding),
                token = token,
                name = name,
                hostelNo = hostelNo,
                rollNumber = rollNumber
            )
            is BottomNavItem.History -> HistoryScreen(
                modifier = Modifier.padding(innerPadding),
                token = token,
                name = name,
                hostelNo = hostelNo,
                rollNumber = rollNumber
            )
        }
    }
}
