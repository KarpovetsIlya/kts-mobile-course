package com.karpovec.kmpmobileapp.feature.main.presentation

import androidx.lifecycle.ViewModel
import com.karpovec.kmpmobileapp.feature.main.domain.RepoUi
import com.karpovec.kmpmobileapp.feature.main.domain.ReposRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MainUiState(
    val items: List<RepoUi> = emptyList()
)

class MainViewModel(
    private val repository: ReposRepository = ReposRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState(items = repository.getList()))
    val state: StateFlow<MainUiState> = _state.asStateFlow()
}