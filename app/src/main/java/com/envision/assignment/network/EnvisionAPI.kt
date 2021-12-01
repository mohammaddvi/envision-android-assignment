package com.envision.assignment.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface EnvisionAPI {
    @Multipart
    @POST("api/test/readDocument")
    suspend fun requestOCR(@Part photo: MultipartBody.Part): OcrProcessingResponseDto
}