package com.envision.assignment.usecase

import com.envision.assignment.repository.EnvisionRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadFileUseCase(private val envisionRepository: EnvisionRepository) {
    suspend fun execute(requestBody: RequestBody){
        envisionRepository.requestOCR(requestBody)
    }
}