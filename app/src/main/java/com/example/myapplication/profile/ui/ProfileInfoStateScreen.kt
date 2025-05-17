package com.example.myapplication.profile.ui

sealed interface ProfileInfoStateScreen {
    data class Content(val data : UserSettingsModel) : ProfileInfoStateScreen
    data class Error(val message: String) : ProfileInfoStateScreen
    data class ConnectionFailed(val data : UserSettingsModel): ProfileInfoStateScreen
 }
