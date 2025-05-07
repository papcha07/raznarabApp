package com.example.myapplication.order.ui.placeOrder

import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.domain.models.Order
import com.example.myapplication.order.domain.models.OrderForView

sealed interface OrdersListState {
    data class Orders(val data: List<OrderForView>) : OrdersListState
    object EmptyList : OrdersListState
}
