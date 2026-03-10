package com.karpovec.kmpmobileapp.feature.main.domain.repository

import com.karpovec.kmpmobileapp.feature.main.domain.dto.SearchRepositoriesResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GithubRepositoriesRepository(
    private val client: HttpClient
) {
    suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int
    ): Result<SearchRepositoriesResponseDto> = withContext(Dispatchers.IO) {
        try {
            val response: SearchRepositoriesResponseDto =
                client.get("https://api.github.com/search/repositories") {
                    parameter("q", query)
                    parameter("page", page)
                    parameter("per_page", perPage)
                }.body()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}