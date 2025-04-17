package com.example.myapplication.settings.domain.api

import android.content.SharedPreferences

interface ThemeRepository {
    fun switchTheme(boolean: Boolean)
    fun getCurrentTheme() : Boolean
    fun getShared() : SharedPreferences
}