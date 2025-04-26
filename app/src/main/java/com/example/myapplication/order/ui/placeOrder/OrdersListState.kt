package com.example.myapplication.order.ui.placeOrder

import com.example.myapplication.order.ui.listOrder.Order

sealed interface OrdersListState {
    data class Orders(val data: List<Order>) : OrdersListState
    object EmptyList : OrdersListState
}
