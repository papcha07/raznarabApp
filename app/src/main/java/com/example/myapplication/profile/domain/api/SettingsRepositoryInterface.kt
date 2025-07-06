package com.example.myapplication.profile.domain.api

import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.profile.domain.model.UserInfoRequest
import com.example.myapplication.profile.ui.UserSettingsModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepositoryInterface {
    fun getUserInfo(userId: String,token: String) : Flow<Resource<UserSettingsModel>>
    fun updateUserInfo(userId: String, token: String, userInfo: UserInfoRequest) : Flow<Resource<UserSettingsModel>>
}