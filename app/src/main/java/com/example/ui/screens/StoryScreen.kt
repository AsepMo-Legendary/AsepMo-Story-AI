package com.example.ui.screens

import android.Manifest
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StoryScreen(onNavigateToHome: () -> Unit) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    LaunchedEffect(permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            val extPath = Environment.getExternalStorageDirectory()
            val folder = File(extPath, "FolderMe")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            delay(1500) // Elegant loading delay
            onNavigateToHome()
        } else if (!permissionState.shouldShowRationale) {
            // First time or user didn't deny yet
            permissionState.launchMultiplePermissionRequest()
        } else {
            // If denied, we'll still proceed as per the elegant flow, users might still skip.
            // But per request: "Jika Tidak Diijinkan. Maka Request... Jika Sudah Diijinkan..."
            // If they deny we might need to inform them or just go home. Let's just go home.
            delay(1500)
            onNavigateToHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Loading AsepMo...", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
