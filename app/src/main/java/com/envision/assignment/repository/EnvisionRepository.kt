package com.envision.assignment.repository

import com.envision.assignment.OcrProcessedModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

interface EnvisionRepository {
    suspend fun requestOCR(photoFile : MultipartBody.Part): OcrProcessedModel
}