package com.example.myapplication.order.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val client: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://geocode-maps.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: GeocoderApi by lazy {
        client.create(GeocoderApi::class.java)
    }

}