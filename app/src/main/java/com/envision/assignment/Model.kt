package com.envision.assignment

import androidx.compose.runtime.Composable
import com.envision.assignment.view.screen.CaptureScreen
import com.envision.assignment.view.screen.LibraryScreen

val tabsData = listOf(
    TabItem.Capture,
    TabItem.Library
)

data class LibraryItem(
    val date: String,
    val time: String
)

sealed class TabItem(var index: Int, var title: String, var screen: @Composable () -> Unit) {
    object Capture : TabItem(index = 0, title = "Capture", screen = { CaptureScreen() })
    object Library : TabItem(index = 1, title = "Library", screen = { LibraryScreen() })
}

data class OcrProcessedModel(
    val paragraphs: List<String>
)