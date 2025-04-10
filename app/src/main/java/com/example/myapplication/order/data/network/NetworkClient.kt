package com.example.myapplication.order.data.network

import com.example.myapplication.order.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any) : Response
}