package com.example.userhostel.auth.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.userhostel.R
import com.example.userhostel.auth.presentation.navigation.Screen
import com.example.userhostel.auth.presentation.viewmodel.FirstScreenViewModel

@Composable
fun FirstScreen(
    navController: NavController,
    viewModel: FirstScreenViewModel = viewModel()
) {
    val rotationAngle by viewModel.rotationAngle.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("MESS", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6C3A00))
                Text("MANAGEMENT", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6C3A00))
                Spacer(modifier = Modifier.height(20.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_plate_fork_knife),
                    contentDescription = "Mess Icon",
                    tint = Color(0xFF6C3A00),
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate(Screen.Login.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE07C3D)),
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(48.dp)
                ) {
                    Text("LOGIN", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(Screen.Signup.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE07C3D)),
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(48.dp)
                ) {
                    Text("SIGN UP", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 1.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bottom_food_image),
                    contentDescription = "Food Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = 100.dp)
                        .rotate(rotationAngle)
                )
            }
        }
    }
}
