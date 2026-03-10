package com.karpovec.androidapp.auth

import android.content.Context
import androidx.core.content.edit

object TokenStorage {

    private const val PREFS_AUTH = "auth"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_AUTH_ERROR = "auth_error"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_AUTH, Context.MODE_PRIVATE)

    fun saveAccessToken(context: Context, token: String) {
        prefs(context).edit {
            putString(KEY_ACCESS_TOKEN, token)
            remove(KEY_AUTH_ERROR)
        }
    }

    fun getAccessToken(context: Context): String? {
        return prefs(context).getString(KEY_ACCESS_TOKEN, null)
    }

    fun saveAuthError(context: Context, message: String) {
        prefs(context).edit {
            putString(KEY_AUTH_ERROR, message)
        }
    }

    fun getAuthError(context: Context): String? {
        return prefs(context).getString(KEY_AUTH_ERROR, null)
    }

    fun clear(context: Context) {
        prefs(context).edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_AUTH_ERROR)
        }
    }
}