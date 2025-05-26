package com.example.myapplication.order.data.network
import com.example.myapplication.order.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any) : Response
    suspend fun professionRequest() : Response
    suspend fun placeOrderRequest(token: String,dto: Any) : Response
    suspend fun getAllOrdersRequest(token: String,userId: String) : Response
    suspend fun deleteOrderById(token: String, orderId : String): Response
    suspend fun getCandidatesByOrderId(token: String, orderId: String): Response
    suspend fun respondToOrder(token: String, orderId: String) : Response
    suspend fun setExecutor(token: String, orderId: String, executorId: String) : Response
    suspend fun completeOrder(token: String, orderId: String, rating: Int) : Response
}