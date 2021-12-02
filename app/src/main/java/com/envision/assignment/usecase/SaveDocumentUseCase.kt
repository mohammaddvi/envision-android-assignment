package com.envision.assignment.usecase

import com.envision.assignment.LibraryItem
import com.envision.assignment.repository.OfflineEnvisionRepository
import java.text.SimpleDateFormat
import java.util.*

class SaveDocumentUseCase(private val envisionRepository: OfflineEnvisionRepository) {
    fun execute(data: String) {
        val stamp = System.currentTimeMillis()
        envisionRepository.saveDocument(
            LibraryItem(
                name = getDateTime(stamp) ?: "",
                content = data
            )
        )
    }

    private fun getDateTime(stamp: Long): String? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm")
            val netDate = Date(stamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}