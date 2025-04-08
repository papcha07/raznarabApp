package com.example.myapplication.profile.data.network

import com.example.myapplication.profile.data.model.UserInfoRequest
import com.example.myapplication.profile.data.model.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.Path

interface GetUserApi {

    @GET("user/{userId}")
    suspend fun getClientInfo(
        @Path("userId") userId: String,
        @Header("Authorization") token: String
    ): UserInfoResponse

    @PATCH("user/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: String,
        @Header("Authorization") token: String,
        @Body userRequest : UserInfoRequest
    ): UserInfoResponse



}