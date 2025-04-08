package com.example.myapplication.order.data.network

import com.example.myapplication.order.data.dto.GeocodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderApi {
    @GET("1.x/")
    suspend fun getAddressList(
        @Query("apikey")
        apiKey : String,
        @Query("geocode")
        geocode: String,
        @Query("format")
        format: String
    ) : GeocodeResponse
}