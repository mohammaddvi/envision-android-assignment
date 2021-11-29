package com.envision.core.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(
    permission: String = android.Manifest.permission.CAMERA,
    permissionNotAvailableContent: @Composable () -> Unit = {},
    permissionNotGrantedContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val permissionState = rememberPermissionState(permission)
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = permissionNotGrantedContent,
        permissionNotAvailableContent = permissionNotAvailableContent,
        content = content
    )
}
