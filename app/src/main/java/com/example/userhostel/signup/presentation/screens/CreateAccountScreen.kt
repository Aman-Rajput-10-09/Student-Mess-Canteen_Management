package com.example.userhostel.signup.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.userhostel.auth.presentation.navigation.Screen
import com.example.userhostel.signup.presentation.events.SignupEvent
import com.example.userhostel.signup.presentation.state.SignupUiState
import com.example.userhostel.signup.viewmodel.SignupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    uiState: SignupUiState,
    viewModel: SignupViewModel,
    navController: NavController
) {
    val data = uiState.data

    val isFormValid = data.name.isNotBlank()
            && data.email.isNotBlank()
            && data.hostelNo.isNotBlank()
            && data.roomNo.isNotBlank()
            && data.phoneNumber.isNotBlank()
            && data.hmsId.isNotBlank()
            && data.rollNumber.isNotBlank()

    val hostelOptions = listOf(
        "BH-1", "BH-2", "BH-3", "BH-4", "BH-5", "BH-6", "BH-7", "BH-8",
        "GH-1", "GH-2", "GH-3", "GH-4", "GH-5", "GH-6", "GH-7", "GH-8", "GH-9", "GH-10", "GH-11",
        "International Hostel"
    )


    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFDF7F0))
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C42)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("← Back", color = Color.White)
                }
            }

            // Form Section (use weight instead of fillMaxSize)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFF8C42),
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Create new\nAccount",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 32.sp
                )

                Text(
                    text = "Already Registered? Log in here.",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                SignupInputField("NAME", data.name) {
                    viewModel.onEvent(SignupEvent.OnDataChange(data.copy(name = it)))
                }

                SignupInputField("EMAIL", data.email) {
                    viewModel.onEvent(SignupEvent.OnDataChange(data.copy(email = it)))
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    // 👇 Apply weight here, on the Box, not inside ExposedDropdownMenuBox
                    Box(modifier = Modifier.weight(1f)) {
                        var expanded by remember { mutableStateOf(false) }

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                readOnly = true,
                                value = data.hostelNo,
                                onValueChange = {},
                                label = { Text("HOSTEL NO.") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFFFEEDB),
                                    unfocusedContainerColor = Color(0xFFFFEEDB),
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color.Black
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                hostelOptions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption) },
                                        onClick = {
                                            viewModel.onEvent(
                                                SignupEvent.OnDataChange(data.copy(hostelNo = selectionOption))
                                            )
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        SignupInputField("ROOM NO.", data.roomNo) {
                            viewModel.onEvent(SignupEvent.OnDataChange(data.copy(roomNo = it)))
                        }
                    }
                }

                SignupInputField("PHONE NUMBER", data.phoneNumber) {
                    viewModel.onEvent(SignupEvent.OnDataChange(data.copy(phoneNumber = it)))
                }

                SignupInputField("HMS ID", data.hmsId) {
                    viewModel.onEvent(SignupEvent.OnDataChange(data.copy(hmsId = it)))
                }

                SignupInputField("HOSTEL ROLL NUMBER", data.rollNumber) {
                    viewModel.onEvent(SignupEvent.OnDataChange(data.copy(rollNumber = it)))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (isFormValid) {
                            viewModel.onEvent(SignupEvent.NextStep)
                        }
                    },
                    enabled = isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFormValid) Color(0xFF4B49AC) else Color.Gray
                    )
                ) {
                    Text("Continue", color = Color.White)
                }
                Spacer(modifier = Modifier.height(32.dp))

                uiState.authError?.let { errorMsg ->
                    Toast.makeText(LocalContext.current, errorMsg, Toast.LENGTH_LONG).show()
                }

            }

            // Bottom login link
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFDF7F0))
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Signup.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("Already Have Account? Login !", color = Color(0xFF4B49AC), fontWeight = FontWeight.Bold)
                }
            }
        }
    }

}

@Composable
fun SignupInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.large,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFFEEDB),
            unfocusedContainerColor = Color(0xFFFFEEDB),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color.Black
        )
    )
}



