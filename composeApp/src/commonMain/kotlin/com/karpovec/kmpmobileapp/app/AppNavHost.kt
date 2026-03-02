package com.karpovec.kmpmobileapp.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karpovec.kmpmobileapp.feature.login.presentation.LoginScreen
import com.karpovec.kmpmobileapp.feature.main.presentation.MainScreen
import com.karpovec.kmpmobileapp.feature.onBoarding.presentation.OnBoardingScreen

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

    NavHost(navController = navController, startDestination = Screen.OnBoarding.route) {

        composable(Screen.OnBoarding.route) {
            OnBoardingScreen (
                onFinish = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.OnBoarding.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onBackClick = {
                    navController.popBackStack(
                        route = Screen.OnBoarding.route,
                        inclusive = false,
                        saveState = false
                    )
                },
                onToggleTheme = onToggleTheme,
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}