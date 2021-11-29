package com.envision.assignment

import com.envision.assignment.network.OcrProcessingResponseDto

fun OcrProcessingResponseDto.toOcrProcessedModel() = OcrProcessedModel(
    this.response.paragraphs.map {
        it.paragraph
    }
)