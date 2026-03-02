package com.karpovec.kmpmobileapp.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karpovec.kmpmobileapp.feature.login.domain.LoginRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository = LoginRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<LoginUiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

    fun onUsernameChanged(value: String) {
        _state.update { old ->
            val newUsername = value
            val isActive = newUsername.isNotBlank() && old.password.isNotBlank()
            old.copy(username = newUsername, isLoginButtonActive = isActive, error = null)
        }
    }

    fun onPasswordChanged(value: String) {
        _state.update { old ->
            val newPassword = value
            val isActive = old.username.isNotBlank() && newPassword.isNotBlank()
            old.copy(password = newPassword, isLoginButtonActive = isActive, error = null)
        }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            val s = state.value

            val result = loginRepository.login(s.username, s.password)

            result.fold(
                onSuccess = {
                    _events.tryEmit(LoginUiEvent.LoginSuccessEvent)
                },
                onFailure = { e ->
                    _state.update { it.copy(error = e.message ?: "Login error") }
                }
            )
        }
    }
}