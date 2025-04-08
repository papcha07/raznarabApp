package com.example.myapplication.profile.domain.api

import com.example.myapplication.profile.data.model.UserInfoRequest
import com.example.myapplication.profile.domain.model.UserSettingsModel
import kotlinx.coroutines.flow.Flow

interface UserInfoUseCaseInterface {
    fun getUserInfo(userId: String ,token: String) : Flow<Pair<UserSettingsModel?, String?>>
    fun updateUserInfo(userId: String, token: String, userInfo: UserSettingsModel) : Flow<Pair<UserSettingsModel?, String?>>
}
