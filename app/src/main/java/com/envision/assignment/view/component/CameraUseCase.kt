package com.envision.assignment.view.component

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun cameraPreview(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
): Pair<PreviewView, ImageCapture> {
    val imageCapture: ImageCapture = ImageCapture.Builder().build()

    val previewView = PreviewView(context)
    val executor = ContextCompat.getMainExecutor(context)
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = androidx.camera.core.Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner, cameraSelector, preview, imageCapture
        )

    }, executor)
    return Pair(previewView, imageCapture)
}

fun takePhoto(
    imageCapture: ImageCapture,
    context: Context,
    onError: (ImageCaptureException) -> Unit,
    onImageSaved: (Uri) -> Unit
) {
    val outputOptions = ImageCapture.OutputFileOptions.Builder(getOutputDirectory(context)).build()
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) = onError(exc)

            override fun onImageSaved(output: ImageCapture.OutputFileResults) =
                onImageSaved(Uri.fromFile(getOutputDirectory(context)))

        })
}

private fun getOutputDirectory(context: Context): File {
    val mDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
    val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        mDateFormat.format(Date()).toString() + ".jpg"
    )
    if (!file.exists()) file.mkdir()
    return file
}