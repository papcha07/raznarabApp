package com.example.myapplication.settings.data

import android.content.SharedPreferences
import com.example.myapplication.CONST
import com.example.myapplication.settings.domain.api.ThemeRepository

class ThemeRepositoryImpl(private val themeSharedPreferences: SharedPreferences) : ThemeRepository {

    override fun switchTheme(boolean: Boolean) {
        themeSharedPreferences.edit()
            .putBoolean(CONST.THEME_SHARED_PREF, boolean)
            .apply()
    }

    override fun getCurrentTheme(): Boolean {
        return themeSharedPreferences
            .getBoolean(CONST.THEME_SHARED_PREF, false)
    }

    override fun getShared(): SharedPreferences {
        return themeSharedPreferences
    }
}