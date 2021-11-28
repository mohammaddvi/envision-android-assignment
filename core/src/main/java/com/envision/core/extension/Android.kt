package com.envision.core.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

val LocalActivity = staticCompositionLocalOf<Activity?> {
    null
}

@Composable
fun ProvideActivity(
    activity: Activity? = LocalContext.current.getActivity(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalActivity provides activity, content = content)
}

private fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }
}