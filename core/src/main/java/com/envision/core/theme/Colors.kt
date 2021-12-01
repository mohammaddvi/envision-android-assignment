package com.envision.core.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val EnvisionLightColors: Colors
    @Composable get() = Colors(
        primary = primaryColor,
        primaryVariant = primaryColor,
        secondary = secondaryColor,
        secondaryVariant = secondaryColor,
        background = defaultBackgroundColor,
        surface = defaultBackgroundColor,
        error = errorColor,
        onPrimary = colorOnPrimary,
        onSecondary = colorOnPrimary,
        onBackground =colorOnPrimary,
        onSurface = colorOnPrimary,
        onError = colorOnPrimary,
        isLight = true,
    )

val primaryColor = Color(0xFF6200EE)
val secondaryColor = Color(0xFF7d22ff)
val defaultBackgroundColor = Color(0xFFFFFFFF)
val errorColor = Color(0xFFee6200)
val colorOnPrimary = Color(0xFFFFFFFF)
val colorOnSecondary = Color(0xFFFFFFFF)
val Colors.grayUnselected: Color get() = Color(0x74FFFFFF)
val Colors.graySeparator: Color get() = Color(0xFF212121)
val Colors.black: Color get() = Color(0xFF000000)