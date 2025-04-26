package com.example.myapplication.order.data.db

import com.example.myapplication.order.domain.api.OrderRepository
import com.example.myapplication.order.ui.listOrder.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderNetworkRepository : OrderRepository {
    override fun getAllOrder(): Flow<List<Order>>  = flow{
    }

    override suspend fun addOrder(order: Order) {

    }

    override suspend fun deleteOrder(order: Order) {

    }

    override suspend fun deleteAll() {
    }
}