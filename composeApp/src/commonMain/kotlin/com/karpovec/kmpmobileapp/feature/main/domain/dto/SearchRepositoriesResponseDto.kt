package com.karpovec.kmpmobileapp.feature.main.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRepositoriesResponseDto(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<RepositoryDto>
)

@Serializable
data class RepositoryDto(
    val id: Long,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val description: String? = null,
    val language: String? = null,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    val owner: OwnerDto
)

@Serializable
data class OwnerDto(
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)