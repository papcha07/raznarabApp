package com.example.myapplication.order.domain.api

import com.example.myapplication.order.ui.Order
import kotlinx.coroutines.flow.Flow

interface OrderInteractorInterface {
    fun getAllOrders() : Flow<List<Order>>
    suspend fun addOrder(order: Order)
    suspend fun deleteOrder(order: Order)
}