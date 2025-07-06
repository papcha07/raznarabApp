package com.example.myapplication.authorization.data.network

import com.example.myapplication.CONST
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRetrofitClient {

    private val client: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(CONST.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private val updateClient : Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(CONST.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        .create()

    val api: AuthApi by lazy {
        client.create(AuthApi::class.java)
    }

}