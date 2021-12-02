package com.envision.assignment.repository

import com.envision.assignment.LibraryItem
import com.envision.assignment.OcrProcessedModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface OnlineEnvsionRepository {
    suspend fun requestOCR(photoFile: MultipartBody.Part): OcrProcessedModel
}

interface OfflineEnvisionRepository {
    fun saveDocument(libraryItem: LibraryItem)
    suspend fun getAllDocuments(): Flow<List<LibraryItem>>
}