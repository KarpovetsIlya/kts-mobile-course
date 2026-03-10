package com.karpovec.androidapp.auth

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.net.toUri
import com.karpovec.androidapp.BuildConfig
import io.github.aakira.napier.Napier
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import java.util.UUID

object GithubOAuthStarter {

    fun start(
        activity: Activity,
        authService: AuthorizationService
    ) {
        Napier.d("Starting GitHub OAuth")
        Napier.d("clientId=${BuildConfig.GITHUB_CLIENT_ID}")
        Napier.d("redirectUri=${BuildConfig.GITHUB_REDIRECT_URI}")

        val authEndpoint = "https://github.com/login/oauth/authorize".toUri()
        val tokenEndpoint = "https://github.com/login/oauth/access_token".toUri()
        val serviceConfig = AuthorizationServiceConfiguration(authEndpoint, tokenEndpoint)

        val state = UUID.randomUUID().toString()
        Napier.d("state=$state")

        val request = AuthorizationRequest.Builder(
            serviceConfig,
            BuildConfig.GITHUB_CLIENT_ID,
            ResponseTypeValues.CODE,
            BuildConfig.GITHUB_REDIRECT_URI.toUri()
        )
            .setScopes("repo", "read:user")
            .setState(state)
            .build()

        val completeIntent = Intent(activity, AuthResultActivity::class.java).apply {
            putExtra("oauth_result", "complete")
            putExtra("expected_state", state)
        }

        val cancelIntent = Intent(activity, AuthResultActivity::class.java).apply {
            putExtra("oauth_result", "cancel")
            putExtra("expected_state", state)
        }

        val flags = pendingIntentFlags()

        val complete = PendingIntent.getActivity(
            activity,
            0,
            completeIntent,
            flags
        )

        val cancel = PendingIntent.getActivity(
            activity,
            1,
            cancelIntent,
            flags
        )

        authService.performAuthorizationRequest(request, complete, cancel)
    }

    private fun pendingIntentFlags(): Int {
        val base = PendingIntent.FLAG_UPDATE_CURRENT
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            base or PendingIntent.FLAG_MUTABLE
        } else {
            base
        }
    }
}