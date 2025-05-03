package com.example.myapplication.order.data.network

import com.example.myapplication.order.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any) : Response
    suspend fun professionRequest() : Response
    suspend fun placeOrderRequest(dto: Any) : Response
}