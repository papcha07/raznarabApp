package com.example.myapplication.order.data.network

import com.example.myapplication.order.data.dto.order.OrderResponse
import com.example.myapplication.order.data.dto.order.OrdersResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.PATCH

interface OrderApi {
    @Multipart
    @POST("order")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Part("Title") title: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("Latitude") latitude: RequestBody,
        @Part("Longitude") longitude: RequestBody,
        @Part("Address") address: RequestBody,
        @Part("Price") price: RequestBody?,
        @Part imagesFiles: List<MultipartBody.Part>,
        @Part("ProfessionId") professionId: RequestBody
    ): OrderResponse


    @GET("order/created-by/{userId}")
    suspend fun getAllOrders(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ) : OrdersResponse


    @GET("image/show/{name}")
    suspend fun getImagesByOrders(
        @Header("Authorization") token: String,
        @Path("name") name : String
    ) : Response<ResponseBody>

    @PATCH("/order/cancel/{orderId}")
    suspend fun deleteOrderById(
        @Header("Authorization") token : String,
        @Path("orderId") orderId: String
    ) : com.example.myapplication.order.data.dto.Response
}