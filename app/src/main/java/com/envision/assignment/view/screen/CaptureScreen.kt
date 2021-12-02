package com.envision.assignment.view.screen

import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.envision.assignment.CaptureScreenState
import com.envision.assignment.R
import com.envision.assignment.view.component.CameraCapture
import com.envision.assignment.view.component.RequestPermissionToCamera
import com.envision.assignment.view.component.takePicture
import com.envision.assignment.viewmodel.CaptureViewModel
import com.envision.core.extension.state
import com.envision.core.theme.EnvisionTheme
import com.envision.core.theme.black
import com.envision.core.theme.grayUnselected
import org.koin.androidx.compose.getViewModel

@Composable
fun CaptureScreen(
    modifier: Modifier = Modifier,
    onButtonGoToLibraryClicked: () -> Unit
) {
    val context = LocalContext.current
    val captureViewModel: CaptureViewModel = getViewModel()
    val captureState = captureViewModel.state()

    val imageCaptureUseCase by remember {
        mutableStateOf(
            ImageCapture.Builder()
                .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()
        )
    }

    val onCaptureClicked = {
        imageCaptureUseCase.takePicture(context, onError = {
            captureViewModel.onImageCaptureException(it)
        }, onImageSaved = {
            captureViewModel.onImageSaved(it)
        })
    }

    Box(modifier = modifier) {
        when (captureState.value.captureScreenState) {
            is CaptureScreenState.Permission -> PermissionState(
                { captureViewModel.onPermissionGranted() },
                { captureViewModel.onPermissionDenied() }
            )
            is CaptureScreenState.ShowingResult -> ShowingResultState(
                ocrResult = captureState.value.captureScreenState as CaptureScreenState.ShowingResult,
                onSaveClicked = { captureViewModel.onSaveDocumentClicked() },
                onGoToLibraryClicked = {
                    onButtonGoToLibraryClicked()
                    captureViewModel.onButtonGoToLibraryClicked()
                }
            )
            is CaptureScreenState.Processing -> ProcessingState(imageCaptureUseCase)
            is CaptureScreenState.Capturing -> CapturingState(imageCaptureUseCase,onCaptureClicked)
            is CaptureScreenState.Error -> CapturingState(imageCaptureUseCase,onCaptureClicked)
        }
    }
}

@Composable
private fun PermissionState(onPermissionGranted: () -> Unit, onPermissionDenied: () -> Unit) {
    RequestPermissionToCamera(onPermissionGranted, onPermissionDenied)
}

@Composable
fun ShowingResultState(
    ocrResult: CaptureScreenState.ShowingResult,
    onSaveClicked: () -> Unit,
    onGoToLibraryClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = ocrResult.result,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(16.dp)
                .background(color = EnvisionTheme.colors.onPrimary)
                .verticalScroll(rememberScrollState(0)),
            color = EnvisionTheme.colors.black
        )
        if (ocrResult.resultIsSaved) {
            Row(
                modifier = Modifier
                    .height(54.dp)
                    .clickable { onGoToLibraryClicked() }
                    .fillMaxWidth()
                    .background(color = EnvisionTheme.colors.grayUnselected)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.text_saved_to_lib),
                    color = EnvisionTheme.colors.black
                )
                Text(
                    text = stringResource(R.string.go_to_lib),
                    color = EnvisionTheme.colors.primary,
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .clickable { onSaveClicked() }
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(50.dp))
                    .height(50.dp)
                    .background(color = EnvisionTheme.colors.primary)
            ) {
                Text(
                    text = stringResource(R.string.save_text_to_lib),
                    modifier = Modifier.align(Alignment.Center),
                    color = EnvisionTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun CapturingState(imageCapture: ImageCapture,onCaptureClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraCapture(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(30.dp)),
            imageCaptureUseCase = imageCapture
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
                text = stringResource(R.string.capture),
                modifier = Modifier.align(Alignment.Center),
                color = EnvisionTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun ProcessingState(imageCapture: ImageCapture) {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraCapture(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(30.dp)),
            imageCaptureUseCase = imageCapture
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
                text = stringResource(R.string.ocr_in_progress),
                modifier = Modifier.align(Alignment.Center),
                color = EnvisionTheme.colors.onPrimary
            )
        }
    }
}