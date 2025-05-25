package com.example.myapplication.order.data.network

import com.example.myapplication.CONST
import com.example.myapplication.raznarab.ui.data.network.RaznarabApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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


    private val professionsClient: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(CONST.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val professionApi: ProfessionApi by lazy {
        professionsClient.create(ProfessionApi::class.java)
    }

    private val orderClient: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(CONST.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val orderApi : OrderApi by lazy {
        orderClient.create(OrderApi::class.java)
    }

    val raznarabApi: RaznarabApi by lazy {
        orderClient.create(RaznarabApi::class.java)
    }

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()



}