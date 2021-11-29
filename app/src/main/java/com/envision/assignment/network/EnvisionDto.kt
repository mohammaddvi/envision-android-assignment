package com.envision.assignment.network

import com.google.gson.annotations.SerializedName

data class OcrProcessingResponseDto(
    @SerializedName("response") val response: OcrResponseDto
)

data class OcrResponseDto(
    @SerializedName("paragraphs") val paragraphs: List<ParagraphDto>
)

data class ParagraphDto(
    @SerializedName("paragraph") val paragraph: String,
    @SerializedName("language") val language: String
)