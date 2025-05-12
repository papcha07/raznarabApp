package com.example.myapplication.raznarab.ui.data.network

import com.example.myapplication.raznarab.ui.data.dto.CoordinatesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RaznarabApi {

    @POST("/user/respond/{orderId}")
    suspend fun respondToOrder(
        @Header("Authorization") token: String,
        @Path("orderId") orderId: String
    ): retrofit2.Response<Unit>


    @GET("order/map")
    suspend fun getCoordinates(
        @Header("Authorization") token: String
    ) : CoordinatesResponse
    
}