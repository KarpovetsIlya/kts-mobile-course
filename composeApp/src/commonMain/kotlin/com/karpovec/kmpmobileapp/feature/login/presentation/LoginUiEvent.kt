package com.karpovec.kmpmobileapp.feature.login.presentation

sealed class LoginUiEvent {
    data object LoginSuccessEvent : LoginUiEvent()
}