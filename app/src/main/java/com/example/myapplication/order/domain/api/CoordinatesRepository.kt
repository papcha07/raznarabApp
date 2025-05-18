package com.example.myapplication.order.domain.api

import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.data.dto.order.OrderResponse
import com.example.myapplication.order.data.network.ImagesResponse
import com.example.myapplication.order.domain.models.Candidate
import com.example.myapplication.order.domain.models.Order
import com.example.myapplication.order.domain.models.OrderForView
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.order.domain.models.Profession
import kotlinx.coroutines.flow.Flow

interface CoordinatesRepository {
    fun searchAddress(address: String): Flow<Resource<MutableList<Place>>>
    fun getProfessions(): Flow<Resource<MutableList<Profession>>>
    fun placeOrder(token: String, order: Order): Flow<String?>
    fun getAllOrders(token: String, userId: String): Flow<Resource<List<OrderDto>>>
    fun getImagesByName(token: String, fileName: String): Flow<Any?>
    fun deleteOrder(token: String, orderId: String): Flow<Boolean>
    fun getCandidatesByOrderId(token: String, orderId: String): Flow<Resource<List<Candidate>>>
    fun respondToOrder(token: String, orderId: String) : Flow<Boolean>
}
