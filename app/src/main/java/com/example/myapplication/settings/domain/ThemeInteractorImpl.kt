package com.example.myapplication.settings.domain

import android.content.SharedPreferences
import com.example.myapplication.settings.domain.api.ThemeInteractor
import com.example.myapplication.settings.domain.api.ThemeRepository

class ThemeInteractorImpl(
    private val themeRepository: ThemeRepository
) : ThemeInteractor {

    override fun getTheme(): Boolean {
        return themeRepository.getCurrentTheme()
    }

    override fun switchTheme(bool: Boolean) {
        themeRepository.switchTheme(bool)
    }

    override fun getShared(): SharedPreferences {
        return themeRepository.getShared()
    }
}