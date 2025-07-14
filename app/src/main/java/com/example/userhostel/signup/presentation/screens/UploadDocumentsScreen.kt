package com.example.userhostel.signup.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.userhostel.R
import com.example.userhostel.signup.presentation.events.SignupEvent
import com.example.userhostel.signup.presentation.state.SignupUiState
import com.example.userhostel.signup.presentation.utils.getFileNameFromUri
import com.example.userhostel.signup.viewmodel.SignupViewModel
import java.io.InputStream

@Composable
fun UploadDocumentsScreen(
    uiState: SignupUiState,
    viewModel: SignupViewModel,
) {
    val context = LocalContext.current

    val hostelPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val fileName = getFileNameFromUri(context, uri)

            inputStream?.use { stream ->
                val bytes = stream.readBytes()
                viewModel.onEvent(SignupEvent.OnUploadDocument(bytes, "hostel"))
                viewModel.onEvent(SignupEvent.UploadHostelId(fileName ?: "Unknown"))
            }
        }
    }

    val hmsPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val fileName = getFileNameFromUri(context, uri)

            inputStream?.use { stream ->
                val bytes = stream.readBytes()
                viewModel.onEvent(SignupEvent.OnUploadDocument(bytes, "hms"))
                viewModel.onEvent(SignupEvent.UploadHmsId(fileName ?: "Unknown"))
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Light Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFDF7F0))
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = { viewModel.onEvent(SignupEvent.GoBack) },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE07C3D)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("← Back", color = Color.White)
            }
        }

        // Main Orange Curved Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFF8C42), shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Upload Documents", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)

            Spacer(modifier = Modifier.height(32.dp))

            UploadBox(
                label = "UPLOAD HOSTEL ID:",
                fileName = uiState.hostelIdFileName,
                onClick = { hostelPickerLauncher.launch("*/*") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            UploadBox(
                label = "UPLOAD HMS ID:",
                fileName = uiState.hmsIdFileName,
                onClick = { hmsPickerLauncher.launch("*/*") }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.onEvent(SignupEvent.OnUploadDocuments) },
                enabled = uiState.isDocumentUploadValid,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B49AC)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Upload and Continue", color = Color.White)
            }
        }
    }
}

@Composable
fun UploadBox(label: String, fileName: String?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFFEEDB))
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.download),
                contentDescription = "Upload",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = fileName ?: "Select File",
                color = if (fileName != null) Color.Black else Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
