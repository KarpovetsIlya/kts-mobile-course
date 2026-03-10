package com.karpovec.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.karpovec.androidapp.auth.GithubOAuthStarter
import com.karpovec.androidapp.auth.TokenStorage
import com.karpovec.kmpmobileapp.app.App
import com.karpovec.kmpmobileapp.core.network.GithubHttpClient
import com.karpovec.kmpmobileapp.core.theme.AppTheme
import com.karpovec.kmpmobileapp.feature.main.domain.repository.GithubRepositoriesRepository
import com.karpovec.kmpmobileapp.feature.main.presentation.MainViewModel
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import net.openid.appauth.AuthorizationService

class MainActivity : ComponentActivity() {

    private lateinit var authorizationService: AuthorizationService

    override fun onCreate(savedInstanceState: Bundle?) {
        Napier.base(DebugAntilog())
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        authorizationService = AuthorizationService(this)

        val authorized = !TokenStorage.getAccessToken(this).isNullOrBlank()

        Napier.d("Saved token = ${TokenStorage.getAccessToken(this)}", tag = "Napier")
        Napier.d("MainActivity: isAuthorized=$authorized", tag = "Napier")

        val client = GithubHttpClient.create(
            tokenProvider = { TokenStorage.getAccessToken(this) },
            onUnauthorized = { TokenStorage.clear(this) }
        )

        val repository = GithubRepositoriesRepository(client)
        val mainViewModel = MainViewModel(repository)

        setContent {
            AppTheme {
                App(
                    onExitApp = { finish() },
                    onStartOAuth = {
                        GithubOAuthStarter.start(
                            activity = this,
                            authService = authorizationService
                        )
                    },
                    isAuthorized = authorized,
                    mainViewModel = mainViewModel
                )
            }
        }
    }

    override fun onDestroy() {
        if (::authorizationService.isInitialized) {
            authorizationService.dispose()
        }
        super.onDestroy()
    }
}