package com.envision.assignment.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.envision.core.theme.EnvisionTheme

enum class CaptureScreenStates {
    Capturing,
    Processing,
    ShowingResult
}

@Composable
fun CaptureScreen() {
    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "hete is capture")
    }
}

@Preview
@Composable
fun CaptureScreenPreview() {
    EnvisionTheme {
        CaptureScreen()
    }
}