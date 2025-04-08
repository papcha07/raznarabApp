package com.example.myapplication.authorization.data.network

import com.example.myapplication.authorization.data.dto.login.LoginRequest
import com.example.myapplication.authorization.data.dto.login.LoginResponse
import com.example.myapplication.authorization.data.dto.registration.RegistrationRequest
import com.example.myapplication.authorization.data.dto.registration.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("/auth/register-user")
    suspend fun registerUser(@Body registrationRequest: RegistrationRequest): RegistrationResponse

    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest) : LoginResponse
}