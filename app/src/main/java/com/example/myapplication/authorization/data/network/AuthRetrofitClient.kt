package com.example.myapplication.authorization.data.network

import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRetrofitClient {

    private val client: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://hw-api-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private val updateClient : Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://hw-api-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        .create()

    val api: AuthApi by lazy {
        client.create(AuthApi::class.java)
    }

}