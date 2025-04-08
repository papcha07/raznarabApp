package com.example.myapplication.profile.domain.api

import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.profile.data.model.UserInfoResponse
import com.example.myapplication.profile.domain.model.UserSettingsModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepositoryInterface {
    fun getUserInfo(userId: String,token: String) : Flow<Resource<UserInfoResponse>>
    fun updateUserInfo(userId: String, token: String, userInfo: UserSettingsModel) : Flow<Resource<UserInfoResponse>>
}