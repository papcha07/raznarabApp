package com.example.myapplication.order.domain.api

import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.data.network.ImagesResponse
import com.example.myapplication.order.domain.models.Candidate
import com.example.myapplication.order.domain.models.Order
import com.example.myapplication.order.domain.models.OrderForView
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Profession
import kotlinx.coroutines.flow.Flow


interface MapInteractorInterface {
    fun execute(query: String) : Flow<Pair<MutableList<Place>?, String?>>
    fun getProfessions() : Flow<Pair<MutableList<Profession>?, String?>>
    fun placeOrder(token : String,order: Order) : Flow<String?>
    fun getAllOrders(token: String,userId: String) : Flow<Pair<List<OrderDto>?, String?>>
    fun deleteOrder(token: String, orderId: String) : Flow<Boolean>
    fun getCandidatesById(token: String, orderId: String) : Flow<Pair<List<Candidate>?, String?>>
    fun respondToOrder(token: String, orderId: String) : Flow<Boolean>
}
