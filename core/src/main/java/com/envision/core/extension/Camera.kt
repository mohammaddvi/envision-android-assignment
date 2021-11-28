package com.envision.core.extension

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

//@Composable
//fun SimpleCameraPreview(analyzer: ImageAnalysis.Analyzer) {
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val context = LocalContext.current
//    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
//
//    AndroidView(
//        factory = { ctx ->
//            val preview = PreviewView(ctx)
//            val executor = ContextCompat.getMainExecutor(ctx)
//            cameraProviderFuture.addListener({
//                val cameraProvider = cameraProviderFuture.get()
//                bindPreview(
//                    lifecycleOwner,
//                    preview,
//                    cameraProvider,
//                    analyzer,
//                    executor
//                )
//            }, executor)
//            preview
//        },
//        modifier = Modifier.fillMaxSize(),
//    )
//}