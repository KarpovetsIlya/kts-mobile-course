package com.karpovec.kmpmobileapp.core.settings

import com.russhwolf.settings.Settings

class AppPrefs(
    private val settings: Settings
) {

    fun isFirstLaunch(): Boolean {
        return settings.getBoolean("is_first_launch", true)
    }

    fun setFirstLaunchDone() {
        settings.putBoolean("is_first_launch", false)
    }
}