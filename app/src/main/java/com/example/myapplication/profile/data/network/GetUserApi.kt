package com.example.myapplication.profile.data.network

import com.example.myapplication.profile.data.model.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

interface GetUserApi {

    @GET("user/{userId}")
    suspend fun getClientInfo(
        @Path("userId") userId: String,
        @Header("Authorization") token: String
    ): UserInfoResponse


    @Multipart
    @PATCH("user/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: String,
        @Header("Authorization") token: String,
        @Part("Email") email: RequestBody?,
        @Part("PhoneNumber") phoneNumber: RequestBody?,
        @Part("FirstName") firstName: RequestBody?,
        @Part("SecondName") secondName: RequestBody?,
        @Part("Patronymic") patronymic: RequestBody?,
        @Part("Description") description: RequestBody?,
        @Part image: MultipartBody.Part?,
        @Part("DeleteAvatar") deleteAvatar: RequestBody?
    ): UserInfoResponse




}