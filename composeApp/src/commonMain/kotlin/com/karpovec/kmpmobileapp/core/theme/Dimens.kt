package com.karpovec.kmpmobileapp.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimens(
    val screenPadding: Dp = 16.dp,

    val spaceXs: Dp = 8.dp,
    val spaceS: Dp = 12.dp,
    val spaceM: Dp = 16.dp,
    val spaceL: Dp = 24.dp,
    val spaceXl: Dp = 32.dp,
    val space2xl: Dp = 48.dp,
    val space3xl: Dp = 72.dp,

    val textFieldCorner: Dp = 20.dp,
    val buttonCorner: Dp = 18.dp,
    val buttonHeight: Dp = 56.dp,
    val avatarSize: Dp = 44.dp,
    val cardCorner: Dp = 28.dp,
    val cardElevation: Dp = 2.dp,
    val onboardingImageSize: Dp = 140.dp,

    val contentWidth: Float = 0.8f,
    val primaryButtonWidth: Float = 0.75f,
)

val LocalDimens = staticCompositionLocalOf { AppDimens() }