package com.example.myapplication.order.data.db

import com.example.myapplication.order.domain.api.OrderRepository
import com.example.myapplication.order.ui.listOrder.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(private val db: OrderDataBase) : OrderRepository {

    override fun getAllOrder(): Flow<List<Order>> = flow {
        val list = db.orderDao().getAllOrders()
        val convertedList = convertToOrder(list)
        emit(convertedList)
    }

    override suspend fun addOrder(order: Order) {
        val transferOrder = convertToEntity(order)
        with(Dispatchers.IO){
            db.orderDao().addOrder(transferOrder)
        }
    }

    override suspend fun deleteOrder(order: Order) {
        val transferOrder = convertToEntity(order)
        with(Dispatchers.IO){
            db.orderDao().deleteOrder(transferOrder)
        }
    }

    override suspend fun deleteAll() {
        db.orderDao().deleteAll()
    }

    private fun convertToOrder(list: List<OrderEntity>) : List<Order>{
        return list.map {
            Order(
                it.orderId,
                it.address,
                it.category,
                it.price,
                it.description,
                it.imageUri
            )
        }
    }

    private fun convertToEntity(order: Order) : OrderEntity{
        return OrderEntity(
            order.orderId,
            order.address,
            order.category,
            order.price,
            order.description,
            order.imageUri
        )
    }


}