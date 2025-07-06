package com.example.myapplication.settings.domain.api

import android.content.SharedPreferences

interface ThemeInteractor {
    fun getTheme() : Boolean
    fun switchTheme(bool: Boolean)
    fun getShared(): SharedPreferences
}