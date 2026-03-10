package com.karpovec.kmpmobileapp.core.network

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object GithubHttpClient {

    fun create(
        tokenProvider: () -> String?,
        onUnauthorized: () -> Unit
    ): HttpClient {
        return HttpClient {
            expectSuccess = false

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message, tag = "Ktor")
                    }
                }
                level = LogLevel.BODY
            }

            install(DefaultRequest) {
                header(HttpHeaders.Accept, "application/vnd.github+json")
                tokenProvider()?.let { token ->
                    header(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            HttpResponseValidator {
                validateResponse { response ->
                    if (response.status.value == 401) {
                        onUnauthorized()
                    }
                }
            }
        }
    }
}