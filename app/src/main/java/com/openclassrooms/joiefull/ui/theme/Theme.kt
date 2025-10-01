package com.openclassrooms.joiefull.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Light color scheme for the app theme.
 */
private val LightColors = lightColorScheme(
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    primary = Color(0xFF000000)
)


/**
 * Dark color scheme for the app theme.
 */
private val DarkColors = darkColorScheme(
    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    primary = Color(0xFFFFFFFF)
)

/**
 * App theme wrapper for Joiefull.
 *
 * Chooses between [LightColors] and [DarkColors] depending on [useDarkTheme].
 *
 * @param useDarkTheme Whether to use dark theme (defaults to system preference).
 * @param content The composable content to be wrapped in the theme.
 */
@Composable
fun JoiefullTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}
