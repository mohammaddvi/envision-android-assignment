package com.envision.assignment.view.screen

import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.envision.assignment.view.component.RequestPermissionToCamera
import com.envision.assignment.view.component.cameraPreview
import com.envision.assignment.view.component.takePhoto
import com.envision.assignment.viewmodel.CaptureViewModel
import com.envision.core.theme.EnvisionTheme
import org.koin.androidx.compose.getViewModel

enum class CaptureScreenStates {
    Permission,
    Capturing,
    Processing,
    ShowingResult
}

@Composable
fun CaptureScreen() {
    val viewModel = getViewModel<CaptureViewModel>()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    Column(modifier = Modifier.fillMaxSize()) {
        RequestPermissionToCamera()
        val cameraPreview = cameraPreview(lifecycleOwner, context, cameraProviderFuture)
        AndroidView(factory = { cameraPreview.first }, modifier = Modifier.weight(1f))
        Button(onClick = {
            takePhoto(cameraPreview.second, context, {}, {
                viewModel.uploadFile(it,context)
            })
        }) {
            Text(text = "Capture")
        }
    }
}


@Preview
@Composable
fun CaptureScreenPreview() {
    EnvisionTheme {
        CaptureScreen()
    }
}