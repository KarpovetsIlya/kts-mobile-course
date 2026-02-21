package com.karpovec.kmpmobileapp.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.karpovec.kmpmobileapp.core.theme.DarkColors
import com.karpovec.kmpmobileapp.core.theme.LightColors


@Composable
fun App() {
    var darkTheme by rememberSaveable { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors
    ) {
        AppNavHost(
            onToggleTheme = { darkTheme = !darkTheme }
        )
    }
}