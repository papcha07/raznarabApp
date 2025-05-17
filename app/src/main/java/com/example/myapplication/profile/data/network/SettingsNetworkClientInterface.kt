package com.example.myapplication.profile.data.network

import com.example.myapplication.authorization.data.dto.Response
import com.example.myapplication.profile.domain.model.UserInfoRequest

interface SettingsNetworkClientInterface {

    suspend fun doRequestInfo(dto: Any): Response
    suspend fun updateInfoRequest(
        userId: String,
        token: String,
        model: UserInfoRequest
    ): Response

}