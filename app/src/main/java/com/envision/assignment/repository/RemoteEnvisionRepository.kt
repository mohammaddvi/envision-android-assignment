package com.envision.assignment.repository

import com.envision.assignment.OcrProcessedModel
import com.envision.assignment.network.EnvisionAPI
import com.envision.assignment.toOcrProcessedModel
import okhttp3.RequestBody

class RemoteEnvisionRepository(private val envisionAPI: EnvisionAPI) : EnvisionRepository {
    override suspend fun requestOCR(photoFile: RequestBody): OcrProcessedModel =
        envisionAPI.requestOCR(photoFile).toOcrProcessedModel()
}