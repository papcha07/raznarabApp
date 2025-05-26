package com.example.myapplication.profile.data.network

import com.example.myapplication.CONST
import com.example.myapplication.order.data.network.RetrofitClient
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProfileRetrofitInstance {


    private val profileRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(CONST.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(defaultGson))
            .client(RetrofitClient.okHttpClient)
            .build()
    }

    val userApi by lazy {
        profileRetrofit.create(GetUserApi::class.java)
    }


    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()


    private val updateRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(CONST.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(patchGson))
            .build()
    }

    private val defaultGson = GsonBuilder().create()

    private val patchGson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    val updateUserApi by lazy {
        updateRetrofit.create(GetUserApi::class.java)
    }


}