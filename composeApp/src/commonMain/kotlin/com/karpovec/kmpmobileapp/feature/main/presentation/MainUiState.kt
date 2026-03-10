package com.karpovec.kmpmobileapp.feature.main.presentation

import com.karpovec.kmpmobileapp.feature.main.domain.dto.RepositoryDto

data class MainUiState(
    val query: String = "",
    val items: List<RepositoryDto> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null,
    val page: Int = 1,
    val perPage: Int = 20,
    val hasMore: Boolean = true
)