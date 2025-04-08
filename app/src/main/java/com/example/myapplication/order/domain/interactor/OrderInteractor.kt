package com.example.myapplication.order.domain.interactor

import com.example.myapplication.order.domain.api.OrderInteractorInterface
import com.example.myapplication.order.domain.api.OrderRepository
import com.example.myapplication.order.ui.Order
import kotlinx.coroutines.flow.Flow

class OrderInteractor(private val orderRepository: OrderRepository) : OrderInteractorInterface {

    override fun getAllOrders(): Flow<List<Order>> {
        return orderRepository.getAllOrder()
    }

    override suspend fun addOrder(order: Order) {
        orderRepository.addOrder(order)
    }

    override suspend fun deleteOrder(order: Order) {
        orderRepository.deleteOrder(order)
    }
}