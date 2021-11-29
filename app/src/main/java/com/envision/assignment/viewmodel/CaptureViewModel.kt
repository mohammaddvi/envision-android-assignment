package com.envision.assignment.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import com.envision.assignment.repository.EnvisionRepository
import com.envision.core.viewmodel.EnvisionStatefulViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class CaptureViewModel(private val uploadFileUseCase: EnvisionRepository) :
    EnvisionStatefulViewModel<CaptureViewModel.State>(State()) {
    data class State(
        val alaki: String = ""
    )


    fun uploadFile(uri: Uri,context: Context) {

        val file: File =  File(uri.path!!)
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
//        val file: File = com.envision.core.utils.FileUtil.getFile(context, uri)
//        val description: RequestBody = RequestBody.create(
//            MultipartBody.FORM, file
//        )
        launch {
            runCatching {

                onIo {
                    uploadFileUseCase.requestOCR(requestFile)
                }
            }.fold({
                Log.d("mogger", "yey")
            }, {
                Log.d("mogger", "failed cause ${it.message}")
            })
        }

    }
    fun getFileFromUri(uri: Uri,context: Context): File? {
        if (uri.path == null) {
            return null
        }
        var realPath = String()
        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (uri.path!!.contains("/document/image:")) {
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else {
            databaseUri = uri
            selection = null
            selectionArgs = null
        }
        try {
            val column = "_data"
            val projection = arrayOf(column)
            val cursor = context.contentResolver.query(
                databaseUri,
                projection,
                selection,
                selectionArgs,
                null
            )
            cursor?.let {
                if (it.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(column)
                    realPath = cursor.getString(columnIndex)
                }
                cursor.close()
            }
        } catch (e: Exception) {
            Log.i("GetFileUri Exception:", e.message ?: "")
        }
        val path = if (realPath.isNotEmpty()) realPath else {
            when {
                uri.path!!.contains("/document/raw:") -> uri.path!!.replace(
                    "/document/raw:",
                    ""
                )
                uri.path!!.contains("/document/primary:") -> uri.path!!.replace(
                    "/document/primary:",
                    "/storage/emulated/0/"
                )
                else -> return null
            }
        }
        return File(path)
    }
}