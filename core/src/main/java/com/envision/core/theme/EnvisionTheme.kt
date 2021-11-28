package com.envision.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.envision.core.extension.ProvideActivity
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun EnvisionTheme(content: @Composable () -> Unit) {
    ProvideActivity {
        ProvideWindowInsets {
            MaterialTheme(
                colors = EnvisionLightColors,
            ) {
                Column(androidx.compose.ui.Modifier.background(EnvisionTheme.colors.background)) {
                    content()
                }
            }
        }
    }
}

typealias EnvisionTheme = MaterialTheme