package com.example.myapplication.order.data.network

import android.credentials.CredentialDescription
import com.example.myapplication.order.data.dto.Response
import com.example.myapplication.order.data.dto.order.OrderResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OrderApi {
    @Multipart
    @POST("order")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Part("Description") description: RequestBody,
        @Part("Latitude") latitude: RequestBody,
        @Part("Longitude") longitude: RequestBody,
        @Part("Address") address: RequestBody,
        @Part("Price") price: RequestBody?,
        @Part imagesFiles: List<MultipartBody.Part>,
        @Part("ProfessionId") professionId: RequestBody
    ): OrderResponse
}