package com.karpovec.kmpmobileapp.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karpovec.kmpmobileapp.core.settings.AppPrefs
import com.karpovec.kmpmobileapp.feature.login.presentation.LoginScreen
import com.karpovec.kmpmobileapp.feature.main.presentation.MainScreen
import com.karpovec.kmpmobileapp.feature.onBoarding.presentation.OnBoardingScreen
import com.russhwolf.settings.Settings

sealed class Screen(val route: String) {
    object OnBoarding : Screen("onBoarding")
    object Login : Screen("login")
    object Main : Screen("main")
}

@Composable
fun AppNavHost(
    onToggleTheme: () -> Unit,
) {
    val navController = rememberNavController()
    val prefs = remember { AppPrefs(Settings()) }
    val startRoute =
        if (prefs.isFirstLaunch()) Screen.OnBoarding.route
        else Screen.Login.route
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(
                onFinish = {
                    prefs.setFirstLaunchDone()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.OnBoarding.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onToggleTheme = onToggleTheme,
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}