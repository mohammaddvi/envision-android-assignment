package com.envision.assignment.repository

import com.envision.assignment.OcrProcessedModel
import okhttp3.RequestBody

interface EnvisionRepository {
    suspend fun requestOCR(photoFile : RequestBody): OcrProcessedModel
}