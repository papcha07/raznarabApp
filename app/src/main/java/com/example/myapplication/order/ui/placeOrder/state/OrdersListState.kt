package com.example.myapplication.order.ui.placeOrder.state

import com.example.myapplication.order.data.dto.order.OrderDto

sealed interface OrdersListState {
    data class Orders(val data: List<OrderDto>) : OrdersListState
    object EmptyList : OrdersListState
    object Loading: OrdersListState
    object Failed: OrdersListState
}
