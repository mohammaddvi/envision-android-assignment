package com.envision.assignment.view.component

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.envision.core.extension.Permission
import com.envision.core.theme.EnvisionTheme
import com.envision.core.theme.black
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionToCamera(onPermissionGranted: () -> Unit, onPermissionDenied: () -> Unit) {
    val context = LocalContext.current
    var isShownRationale by rememberSaveable { mutableStateOf(false) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermissionState.hasPermission) {
        onPermissionGranted()
        return
    }
    Permission(
        permission = Manifest.permission.CAMERA,
        permissionNotAvailableContent = {
            onPermissionDenied()
            PermissionDenied {
                navigateToSettingsScreen(context)
            }
        },
        permissionNotGrantedContent = {
            onPermissionDenied()
            if (isShownRationale) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Feature is not available",
                        color = EnvisionTheme.colors.black
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text(text = "Request Permission", color = EnvisionTheme.colors.black)
                    }
                }
            } else {
                Rationale(
                    onDoNotShowRationale = { isShownRationale = true },
                    onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
                )
            }
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
        Text("Camera permission denied.", color = EnvisionTheme.colors.black)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = navigateToSettingsScreen) {
            Text("Open Settings", color = EnvisionTheme.colors.black)
        }
    }
}

@Composable
private fun Rationale(
    onDoNotShowRationale: () -> Unit,
    onRequestPermission: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "The camera is important for this app. Please grant the permission.",
            color = EnvisionTheme.colors.black,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = onRequestPermission, modifier = Modifier.weight(1f)) {
                Text("Request permission")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onDoNotShowRationale, modifier = Modifier.weight(1f)) {
                Text("Don't show rationale again")
            }
        }
    }
}

@Preview
@Composable
fun PermissionDeniedPreview() {
    EnvisionTheme {
        PermissionDenied {}
    }
}

@Preview
@Composable
fun PermissionRationale() {
    EnvisionTheme {
        Rationale(onDoNotShowRationale = {}, onRequestPermission = {})
    }
}