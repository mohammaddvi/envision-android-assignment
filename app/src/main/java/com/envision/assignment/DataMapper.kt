package com.envision.assignment

import com.envision.assignment.network.OcrProcessingResponseDto
import com.envision.assignment.roomtest.Document

fun OcrProcessingResponseDto.toOcrProcessedModel() = OcrProcessedModel(
    this.response.paragraphs.map {
        it.paragraph
    }
)

fun Document.toLibraryItem() = LibraryItem(
    this.documentName,
    this.documentName,
    this.documentText
)