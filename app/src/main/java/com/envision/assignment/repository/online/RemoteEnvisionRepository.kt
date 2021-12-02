package com.envision.assignment.repository.online

import com.envision.assignment.*
import com.envision.assignment.network.EnvisionAPI
import com.envision.assignment.repository.OnlineEnvsionRepository
import okhttp3.MultipartBody

class RemoteEnvisionRepository(
    private val envisionAPI: EnvisionAPI,
) : OnlineEnvsionRepository {
    override suspend fun requestOCR(photoFile: MultipartBody.Part): OcrProcessedModel =
        envisionAPI.requestOCR(photoFile).toOcrProcessedModel()
}