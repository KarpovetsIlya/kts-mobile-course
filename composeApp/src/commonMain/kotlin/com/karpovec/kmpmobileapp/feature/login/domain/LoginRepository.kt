package com.karpovec.kmpmobileapp.feature.login.domain

class LoginRepository {

    fun login(username: String, password: String): Result<Unit> {
        val ok = (username == "test" && password == "1234")
        return if (ok) Result.success(Unit)
        else Result.failure(IllegalArgumentException("Неверный логин или пароль"))
    }
}