package com.example.myapplication.raznarab.ui.data.dto

import com.example.myapplication.order.data.dto.Response
import com.example.myapplication.raznarab.ui.domain.dto.MapOrder

data class OrderInfoResponse(
    val orders: List<MapOrder>
) : Response()
