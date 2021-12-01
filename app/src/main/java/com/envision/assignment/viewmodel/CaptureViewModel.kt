package com.envision.assignment.viewmodel

import android.net.Uri
import androidx.camera.core.ImageCaptureException
import com.envision.assignment.usecase.ConcatParagraphsUseCase
import com.envision.assignment.usecase.UploadFileUseCase
import com.envision.core.coroutine.CoroutineDispatcherProvider
import com.envision.core.errorhandling.ErrorParser
import com.envision.core.utils.LoadableData
import com.envision.core.viewmodel.EnvisionStatefulViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class CaptureViewModel(
    private val uploadFileUseCase: UploadFileUseCase,
    private val concatParagraphsUseCase: ConcatParagraphsUseCase,
    private val errorParser: ErrorParser,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) :
    EnvisionStatefulViewModel<CaptureViewModel.State>(State(), coroutineDispatcherProvider) {
    data class State(
        val ocrResult: LoadableData<String> = LoadableData.NotLoaded,
        val capturing: LoadableData<String> = LoadableData.NotLoaded
    )

    fun onImageSaved(uri: Uri) {
        applyState {
            copy(capturing = LoadableData.Loaded("success"))
        }
        uploadFile(preparingImageFile(uri))
    }

    fun onImageCaptureException(exception: ImageCaptureException) {
        applyState {
            copy(capturing = LoadableData.Failed(exception, exception.message))
        }
    }

    private fun uploadFile(file: MultipartBody.Part) {
        launch {
            applyState {
                copy(ocrResult = LoadableData.Loading)
            }
            runCatching {
                onIo {
                    uploadFileUseCase.execute(file)
                }
            }.fold({
                val result = concatParagraphsUseCase.execute(it.paragraphs)
                applyState {
                    copy(ocrResult = LoadableData.Loaded(result))
                }
            }, {
                applyState {
                    copy(ocrResult = LoadableData.Failed(it, errorParser.parse(it)))
                }
            })
        }
    }

    private fun preparingImageFile(uri: Uri): MultipartBody.Part {
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), File(uri.path!!))
        return MultipartBody.Part.createFormData("photo", "uploadFile", requestFile)
    }
}