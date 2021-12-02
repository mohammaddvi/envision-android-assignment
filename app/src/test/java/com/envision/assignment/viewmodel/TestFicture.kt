package com.envision.assignment.viewmodel

import com.envision.assignment.LibraryItem
import com.envision.assignment.OcrProcessedModel

const val fakeOcrResultConcated = "hi this is a test"
val fakeOcrResultModel = OcrProcessedModel(listOf("hi", "this is a test"))
val fakeThrowable = Throwable("I can't process")
val fakeLibItems = listOf(
    LibraryItem(
        "1/1/1",
        "the sun will come out tomorrow bet your bottom dollar that tomorrow"
    ),
)
val fakeLibraryItem = LibraryItem(
    "1/1/1",
    "the sun will come out tomorrow bet your bottom dollar that tomorrow"
)