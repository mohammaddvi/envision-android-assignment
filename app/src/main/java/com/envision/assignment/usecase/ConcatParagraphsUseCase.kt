package com.envision.assignment.usecase

class ConcatParagraphsUseCase {
    fun execute(paragraphs: List<String>) =
        paragraphs.reduce { acc, paragraph -> "$acc\n$paragraph" }
}