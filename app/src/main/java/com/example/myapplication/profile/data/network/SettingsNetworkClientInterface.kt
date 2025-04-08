package com.example.myapplication.profile.data.network

import com.example.myapplication.authorization.data.dto.Response

interface SettingsNetworkClientInterface {

    suspend fun doRequestInfo(dto: Any) : Response
    suspend fun updateInfoRequest(dto1: Any, dto2: Any) : Response

}