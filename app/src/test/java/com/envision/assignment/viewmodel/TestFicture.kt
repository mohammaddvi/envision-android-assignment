package com.envision.assignment.viewmodel

import com.envision.assignment.OcrProcessedModel

val ocrResultModel = OcrProcessedModel(listOf("hi", "this is a test"))

const val ocrResultConcated = "hi this is a test"

val envisionThrowable = Throwable("I can't process")