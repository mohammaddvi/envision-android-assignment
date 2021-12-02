package com.envision.assignment

import com.envision.assignment.network.OcrProcessingResponseDto
import com.envision.assignment.repository.offline.Document

fun OcrProcessingResponseDto.toOcrProcessedModel() = OcrProcessedModel(
    this.response.paragraphs.map {
        it.paragraph
    }
)

fun Document.toLibraryItem() = LibraryItem(
    this.name,
    this.content
)

fun LibraryItem.toDocument() = Document(
    name = this.name,
    content = this.content
)

fun Document.toDocumentModel() = LibraryItem(
    this.name,
    this.content
)