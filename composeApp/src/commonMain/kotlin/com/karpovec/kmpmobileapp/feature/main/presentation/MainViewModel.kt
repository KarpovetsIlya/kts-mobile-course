package com.karpovec.kmpmobileapp.feature.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karpovec.kmpmobileapp.feature.main.domain.dto.SearchRepositoriesResponseDto
import com.karpovec.kmpmobileapp.feature.main.domain.repository.GithubRepositoriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: GithubRepositoriesRepository
) : ViewModel() {

    private val queryFlow = MutableStateFlow("android")

    private val _state = MutableStateFlow(
        MainUiState(
            query = "android",
            isLoading = true
        )
    )
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        observeSearch()
    }

    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
        queryFlow.value = query
    }

    fun retry() {
        val query = _state.value.query.trim()
        if (query.isNotBlank()) {
            queryFlow.value = query
        }
    }

    fun loadNextPage() {
        val current = _state.value

        if (current.isLoading) return
        if (current.isLoadingMore) return
        if (!current.hasMore) return
        if (current.query.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true, errorMessage = null) }

            val nextPage = current.page + 1
            val result = repository.searchRepositories(
                query = current.query,
                page = nextPage,
                perPage = current.perPage
            )

            result
                .onSuccess { response ->
                    val newItems = response.items
                    _state.update {
                        it.copy(
                            items = it.items + newItems,
                            isLoadingMore = false,
                            page = nextPage,
                            hasMore = newItems.size >= it.perPage
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoadingMore = false,
                            errorMessage = error.message ?: "Ошибка загрузки следующей страницы"
                        )
                    }
                }
        }
    }

    private fun observeSearch() {
        viewModelScope.launch {
            queryFlow
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest { rawQuery ->
                    flow {
                        val query = rawQuery.trim()

                        if (query.isBlank()) {
                            emit(SearchResult.EmptyQuery)
                            return@flow
                        }

                        emit(SearchResult.Loading(query))

                        val result = repository.searchRepositories(
                            query = query,
                            page = 1,
                            perPage = _state.value.perPage
                        )

                        emit(SearchResult.Data(query, result))
                    }
                }
                .catch { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            items = emptyList(),
                            errorMessage = error.message ?: "Unknown error",
                            page = 1,
                            hasMore = false
                        )
                    }
                }
                .collect { searchResult ->
                    when (searchResult) {
                        is SearchResult.EmptyQuery -> {
                            _state.update {
                                it.copy(
                                    items = emptyList(),
                                    isLoading = false,
                                    isLoadingMore = false,
                                    errorMessage = null,
                                    page = 1,
                                    hasMore = false
                                )
                            }
                        }

                        is SearchResult.Loading -> {
                            _state.update {
                                it.copy(
                                    query = searchResult.query,
                                    items = emptyList(),
                                    isLoading = true,
                                    isLoadingMore = false,
                                    errorMessage = null,
                                    page = 1,
                                    hasMore = true
                                )
                            }
                        }

                        is SearchResult.Data -> {
                            searchResult.result
                                .onSuccess { response ->
                                    applyFirstPageSuccess(searchResult.query, response)
                                }
                                .onFailure { error ->
                                    _state.update {
                                        it.copy(
                                            query = searchResult.query,
                                            items = emptyList(),
                                            isLoading = false,
                                            isLoadingMore = false,
                                            errorMessage = error.message ?: "Unknown error",
                                            page = 1,
                                            hasMore = false
                                        )
                                    }
                                }
                        }
                    }
                }
        }
    }

    private fun applyFirstPageSuccess(
        query: String,
        response: SearchRepositoriesResponseDto
    ) {
        val newItems = response.items
        _state.update {
            it.copy(
                query = query,
                items = newItems,
                isLoading = false,
                isLoadingMore = false,
                errorMessage = null,
                page = 1,
                hasMore = newItems.size >= it.perPage
            )
        }
    }

    private sealed class SearchResult {
        data object EmptyQuery : SearchResult()
        data class Loading(val query: String) : SearchResult()
        data class Data(
            val query: String,
            val result: Result<SearchRepositoriesResponseDto>
        ) : SearchResult()
    }
}