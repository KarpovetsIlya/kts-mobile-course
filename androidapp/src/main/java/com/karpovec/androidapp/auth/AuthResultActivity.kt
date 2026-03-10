package com.karpovec.androidapp.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.karpovec.androidapp.BuildConfig
import com.karpovec.androidapp.MainActivity
import io.github.aakira.napier.Napier
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientSecretPost

class AuthResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Napier.d("AuthResultActivity opened", tag = "Napier")
        Napier.d("oauth_result=${intent.getStringExtra("oauth_result")}", tag = "Napier")
        Napier.d("expected_state=${intent.getStringExtra("expected_state")}", tag = "Napier")
        Napier.d("Intent data=${intent.data}", tag = "Napier")
        Napier.d("Intent extras keys=${intent.extras?.keySet()?.joinToString()}", tag = "Napier")

        val resp = AuthorizationResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)

        Napier.d("AuthorizationResponse=$resp", tag = "Napier")
        Napier.d("AuthorizationException=$ex", tag = "Napier")

        if (resp == null) {
            val msg = buildString {
                append("Authorization canceled/failed. ")
                append("type=").append(ex?.type).append(" ")
                append("error=").append(ex?.error).append(" ")
                append("desc=").append(ex?.errorDescription)
            }
            TokenStorage.saveAuthError(this, msg)
            goHome()
            return
        }

        val expected = intent.getStringExtra("expected_state")
        Napier.d("resp.state=${resp.state}", tag = "Napier")
        Napier.d("resp.code=${resp.authorizationCode}", tag = "Napier")

        if (!expected.isNullOrBlank() && resp.state != expected) {
            TokenStorage.saveAuthError(
                this,
                "State mismatch! expected=$expected actual=${resp.state}"
            )
            goHome()
            return
        }

        val authService = AuthorizationService(this)
        val tokenRequest = resp.createTokenExchangeRequest()
        val clientAuth = ClientSecretPost(BuildConfig.GITHUB_CLIENT_SECRET)

        Napier.d("Starting AppAuth token exchange (PKCE-aware)", tag = "Napier")

        authService.performTokenRequest(tokenRequest, clientAuth) { tokenResp, tokenEx ->
            Napier.d("Token response=$tokenResp", tag = "Napier")
            Napier.d("Token exception=$tokenEx", tag = "Napier")

            val token = tokenResp?.accessToken
            if (!token.isNullOrBlank()) {
                Napier.i("Token received! len=${token.length}", tag = "Napier")
                TokenStorage.saveAccessToken(this, token)
            } else {
                TokenStorage.saveAuthError(
                    this,
                    tokenEx?.errorDescription ?: "Token exchange failed"
                )
            }

            authService.dispose()
            goHome()
        }
    }

    private fun goHome() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        finish()
    }
}