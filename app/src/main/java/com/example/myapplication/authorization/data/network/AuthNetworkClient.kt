package com.example.myapplication.authorization.data.network

import com.example.myapplication.authorization.data.dto.Response

interface AuthNetworkClient {

    suspend fun doRequest(
        dto: Any
    ) : Response

    suspend fun doLoginRequest(
        dto: Any
    ) : Response
}
