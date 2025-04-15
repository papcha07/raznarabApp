package com.example.myapplication.profile.ui

import com.example.myapplication.profile.domain.model.UserSettingsModel

sealed interface ProfileInfoStateScreen {
    data class Content(val data : UserSettingsModel) : ProfileInfoStateScreen
    data class Error(val message: String) : ProfileInfoStateScreen
    object ConnectionFailed: ProfileInfoStateScreen
 }
