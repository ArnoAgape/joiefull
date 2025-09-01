package com.openclassrooms.joiefull.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    background = Color(0xFFFFFFFF),        // Blanc
    onBackground = Color(0xFF000000),      // Texte sur fond clair = noir
    primary = Color(0xFF000000)            // Couleur des titres (ici noir)
)

private val DarkColors = darkColorScheme(
    background = Color(0xFF000000),        // Noir
    onBackground = Color(0xFFFFFFFF),      // Texte sur fond sombre = blanc
    primary = Color(0xFFFFFFFF)            // Couleur des titres (ici blanc)
)

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
