package com.example.myapplication.raznarab.ui.data.network

import com.example.myapplication.order.data.dto.Response

interface MapNetworkClientInterface {
    suspend fun getAllOrders(token: String) : Response
}
