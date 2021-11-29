package com.envision.assignment.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import com.envision.assignment.repository.EnvisionRepository
import com.envision.core.viewmodel.EnvisionStatefulViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class CaptureViewModel(private val uploadFileUseCase: EnvisionRepository) :
    EnvisionStatefulViewModel<CaptureViewModel.State>(State()) {
    data class State(
        val alaki: String = ""
    )


    fun uploadFile(uri: Uri,context: Context) {
        val file: File = com.envision.core.utils.FileUtil.getFile(context, uri)
        val description: RequestBody = RequestBody.create(
            MultipartBody.FORM, file
        )
        launch {
            runCatching {

                onIo {
                    uploadFileUseCase.requestOCR(description)
                }
            }.fold({
                Log.d("mogger", "yey")
            }, {
                Log.d("mogger", "failed cause ${it.message}")
            })
        }

    }

}