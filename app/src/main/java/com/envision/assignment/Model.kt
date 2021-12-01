package com.envision.assignment

sealed class CaptureScreenState {
    object Permission : CaptureScreenState()
    object Processing : CaptureScreenState()
    object Capturing : CaptureScreenState()
    data class ShowingResult(val result: String) : CaptureScreenState()
    data class Error(val error: String) : CaptureScreenState()
}

val tabsData = listOf(
    TabItem.Capture,
    TabItem.Library
)

data class LibraryItem(
    val date: String,
    val time: String,
    val content: String
)

sealed class TabItem(var index: Int, var title: String) {
    object Capture : TabItem(index = 0, title = "Capture")
    object Library : TabItem(index = 1, title = "Library")
}

data class OcrProcessedModel(
    val paragraphs: List<String>
)

