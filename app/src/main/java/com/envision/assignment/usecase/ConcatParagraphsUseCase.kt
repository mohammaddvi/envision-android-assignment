package com.envision.assignment.usecase

import com.envision.assignment.roomtest.Document
import com.envision.assignment.roomtest.DocumentDao

class ConcatParagraphsUseCase(private val documentDao: DocumentDao) {
    fun execute(paragraphs: List<String>) = paragraphs.reduce { acc, paragraph -> "$acc\n$paragraph" }.also {
        documentDao.insertDocument(Document(documentName = "1",documentText = it))
    }
}