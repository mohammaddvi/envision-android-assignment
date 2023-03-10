package com.envision.assignment.viewmodel

import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCaptureException
import com.envision.assignment.CaptureScreenState
import com.envision.assignment.usecase.ConcatParagraphsUseCase
import com.envision.assignment.usecase.SaveDocumentUseCase
import com.envision.assignment.usecase.UploadFileUseCase
import com.envision.core.coroutine.CoroutineDispatcherProvider
import com.envision.core.errorhandling.ErrorParser
import com.envision.core.viewmodel.EnvisionStatefulViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CaptureViewModel(
    private val uploadFileUseCase: UploadFileUseCase,
    private val concatParagraphsUseCase: ConcatParagraphsUseCase,
    private val saveDocumentUseCase: SaveDocumentUseCase,
    private val errorParser: ErrorParser,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : EnvisionStatefulViewModel<CaptureViewModel.State>(State(), coroutineDispatcherProvider) {

    data class State(val captureScreenState: CaptureScreenState = CaptureScreenState.Permission)

    fun onImageSaved(uri: Uri) {
        uploadFile(preparingImageFile(uri))
    }

    fun onImageCaptureException(exception: ImageCaptureException) {
        applyState {
            copy(
                captureScreenState = CaptureScreenState.Error(
                    exception.message ?: "please try again"
                )
            )
        }
    }

    fun onButtonGoToLibraryClicked() {
        applyState {
            copy(captureScreenState = CaptureScreenState.Capturing)
        }
    }

    fun onSaveDocumentClicked() {
        val currentContent =
            (currentState.captureScreenState as CaptureScreenState.ShowingResult).result
        launch {
            runCatching {
                onBg {
                    saveDocumentUseCase.execute(currentContent)
                }
            }.fold(onSuccess = {
                applyState {
                    copy(
                        captureScreenState = CaptureScreenState.ShowingResult(
                            currentContent,
                            true
                        )
                    )
                }
            }, onFailure = {
                applyState {
                    copy(
                        captureScreenState = CaptureScreenState.ShowingResult(
                            currentContent,
                            false
                        )
                    )
                }
            })
        }
    }

    fun onPermissionGranted() {
        applyState {
            copy(captureScreenState = CaptureScreenState.Capturing)
        }
    }

    fun onPermissionDenied() {
        applyState {
            copy(captureScreenState = CaptureScreenState.Permission)
        }
    }

    private fun uploadFile(file: MultipartBody.Part) {
        launch {
            applyState {
                copy(captureScreenState = CaptureScreenState.Processing)
            }
            runCatching {
                onIo {
                    uploadFileUseCase.execute(file)
                }
            }.fold({
                val result = concatParagraphsUseCase.execute(it.paragraphs)
                applyState {
                    copy(captureScreenState = CaptureScreenState.ShowingResult(result))
                }
            }, {
                Log.d("mogger","throwable is $it")
                applyState {
                    copy(captureScreenState = CaptureScreenState.Error(errorParser.parse(it).message))
                }
            })
        }
    }

    private fun preparingImageFile(uri: Uri): MultipartBody.Part {
        val file = File(uri.path!!)
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData("photo", file.name, requestFile)
    }
}