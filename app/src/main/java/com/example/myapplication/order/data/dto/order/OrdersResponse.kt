package com.example.myapplication.order.data.dto.order

import com.example.myapplication.order.data.dto.Response

data class OrdersResponse(
    val orders: List<OrderDto>
) : Response()
