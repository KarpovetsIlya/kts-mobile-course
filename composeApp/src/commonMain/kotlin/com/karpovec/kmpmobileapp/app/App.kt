package com.karpovec.kmpmobileapp.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.karpovec.kmpmobileapp.core.theme.DarkColors
import com.karpovec.kmpmobileapp.core.theme.LightColors
import com.karpovec.kmpmobileapp.feature.main.presentation.MainViewModel


@Composable
fun App(onExitApp: () -> Unit, onStartOAuth: () -> Unit, mainViewModel: MainViewModel, isAuthorized: Boolean) {
    var darkTheme by rememberSaveable { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors
    ) {
        AppNavHost(
            onToggleTheme = { darkTheme = !darkTheme },
            onStartOAuth = onStartOAuth,
            isAuthorized = isAuthorized,
            mainViewModel = mainViewModel
        )
    }
}