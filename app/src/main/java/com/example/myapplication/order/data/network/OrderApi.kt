package com.example.myapplication.order.data.network

import android.credentials.CredentialDescription
import com.example.myapplication.order.data.dto.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OrderApi {
    @Multipart
    @POST("orders")
    suspend fun createOrder(
        @Part("description") description: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("address") address: RequestBody,
        @Part("price") price: RequestBody?,
        @Part imagesFiles: List<MultipartBody.Part>,
        @Part("professionId") professionId: RequestBody
    ): Response
}