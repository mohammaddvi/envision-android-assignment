package com.envision.assignment.view.screen

import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.envision.assignment.view.component.RequestPermissionToCamera
import com.envision.assignment.view.component.cameraPreview
import com.envision.assignment.view.component.takePhoto
import com.envision.assignment.viewmodel.CaptureViewModel
import com.envision.core.extension.state
import com.envision.core.theme.EnvisionTheme
import com.envision.core.theme.black
import com.envision.core.utils.LoadableData
import org.koin.androidx.compose.getViewModel

@Composable
fun CaptureScreen() {
    val viewModel = getViewModel<CaptureViewModel>()
    val captureState = viewModel.state()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraPreview = cameraPreview(lifecycleOwner, context, cameraProviderFuture)
    val onCaptureClicked = {
        takePhoto(cameraPreview.second, context, { viewModel.onImageCaptureException(it) }, {
            viewModel.onImageSaved(it)
        })
    }
    Box(modifier = Modifier.fillMaxSize()) {
        RequestPermissionToCamera()

        when (captureState.value.ocrResult) {
            is LoadableData.Loading -> ProcessingState(cameraPreview.first)
            is LoadableData.Loaded -> ShowingResultState(captureState.value.ocrResult.data!!)
            is LoadableData.NotLoaded -> CapturingState(cameraPreview.first, onCaptureClicked)
            is LoadableData.Failed -> CapturingState(cameraPreview.first, onCaptureClicked)
        }
    }
}

@Composable
fun ShowingResultState(ocrResult: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = ocrResult,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = EnvisionTheme.colors.onPrimary)
                .verticalScroll(rememberScrollState(0)),
            color = EnvisionTheme.colors.black
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(50.dp))
                .height(50.dp)
                .background(color = EnvisionTheme.colors.primary)
        ) {
            Text(
                text = "SAVE TEXT TO LIBRARY",
                modifier = Modifier.align(Alignment.Center),
                color = EnvisionTheme.colors.onPrimary
            )
        }
    }
}


@Composable
fun CapturingState(previewView: PreviewView, onCaptureClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(30.dp))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(50.dp))
                .height(50.dp)
                .background(color = EnvisionTheme.colors.primary)
                .clickable { onCaptureClicked() }) {
            Text(
                text = "CAPTURE",
                modifier = Modifier.align(Alignment.Center),
                color = EnvisionTheme.colors.onPrimary
            )
        }
    }
}


@Composable
fun ProcessingState(previewView: PreviewView) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(30.dp))
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 32.dp)
                .fillMaxWidth(0.6f)
                .height(50.dp)
                .background(color = EnvisionTheme.colors.primary)
        ) {
            Text(
                text = "OCR in progress...",
                modifier = Modifier.align(Alignment.Center),
                color = EnvisionTheme.colors.onPrimary
            )
        }
    }
}