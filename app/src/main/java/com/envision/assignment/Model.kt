package com.envision.assignment

data class LibraryItem(
    val name:String,
    val content:String
)
sealed class LibraryScreenState {
    object ShowingLibraryItems : LibraryScreenState()
    data class ReadingItemContent(val content: String) : LibraryScreenState()
}

sealed class CaptureScreenState {
    object Permission : CaptureScreenState()
    object Processing : CaptureScreenState()
    object Capturing : CaptureScreenState()
    data class ShowingResult(val result: String, val resultIsSaved: Boolean = false) :
        CaptureScreenState()

    data class Error(val error: String) : CaptureScreenState()
}

val tabsData = listOf(
    TabItem.Capture,
    TabItem.Library
)

sealed class TabItem(var index: Int, var title: String) {
    object Capture : TabItem(index = 0, title = "CAPTURE")
    object Library : TabItem(index = 1, title = "LIBRARY")
}

data class OcrProcessedModel(
    val paragraphs: List<String>
)

