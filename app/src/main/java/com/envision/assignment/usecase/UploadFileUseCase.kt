package com.envision.assignment.usecase

import com.envision.assignment.repository.OnlineEnvsionRepository
import okhttp3.MultipartBody


class UploadFileUseCase(private val envisionRepository: OnlineEnvsionRepository) {
    suspend fun execute(requestBody: MultipartBody.Part)=
        envisionRepository.requestOCR(requestBody)
}