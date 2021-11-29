package com.envision.assignment.view.component

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.envision.core.extension.Permission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionToCamera() {
    val context = LocalContext.current
    var isShownRationale by rememberSaveable { mutableStateOf(false) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermissionState.hasPermission) return
    Permission(
        permission = Manifest.permission.CAMERA,
        permissionNotAvailableContent = {
            PermissionDenied {
                navigateToSettingsScreen(context)
            }
        },
        permissionNotGrantedContent = {
            if (isShownRationale) {
                Text("Feature not available")
            } else {
                Rationale(
                    onDoNotShowRationale = { isShownRationale = true },
                    onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
                )
            }
        }, content = {
            Text(text = "I love documents, let's read it")
        }
    )
}

private fun navigateToSettingsScreen(context: Context) {
    ContextCompat.startActivity(
        context,
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", "com.envision.assignment", null)
        ),
        null
    )
}

@Composable
private fun PermissionDenied(
    navigateToSettingsScreen: () -> Unit
) {
    Column {
        Text("Camera permission denied.")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = navigateToSettingsScreen) {
            Text("Open Settings")
        }
    }
}

@Composable
private fun Rationale(
    onDoNotShowRationale: () -> Unit,
    onRequestPermission: () -> Unit
) {
    Column {
        Text("The camera is important for this app. Please grant the permission.")
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = onRequestPermission) {
                Text("Request permission")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onDoNotShowRationale) {
                Text("Don't show rationale again")
            }
        }
    }
}