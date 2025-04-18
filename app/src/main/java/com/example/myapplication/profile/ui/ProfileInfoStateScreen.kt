package com.example.myapplication.profile.ui

import com.example.myapplication.authorization.domain.model.User
import com.example.myapplication.profile.domain.model.UserSettingsModel

sealed interface ProfileInfoStateScreen {
    data class Content(val data : UserSettingsModel) : ProfileInfoStateScreen
    data class Error(val message: String) : ProfileInfoStateScreen
    data class ConnectionFailed(val data : UserSettingsModel): ProfileInfoStateScreen
 }
