package com.karpovec.kmpmobileapp.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    val dimens = AppDimens()

    CompositionLocalProvider(
        LocalDimens provides dimens
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = AppTypography,
            content = content
        )
    }
}