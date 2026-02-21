package com.karpovec.kmpmobileapp.app

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karpovec.kmpmobileapp.feature.login.LoginScreen
import com.karpovec.kmpmobileapp.feature.onBoarding.WelcomeScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onToggleTheme = onToggleTheme
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onClick = {
                    navController.popBackStack(
                        route = Screen.Welcome.route,
                        inclusive = false,
                        saveState = true
                    )
                },
                onToggleTheme = onToggleTheme
            )
        }
    }
}