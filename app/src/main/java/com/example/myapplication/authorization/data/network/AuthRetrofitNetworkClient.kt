package com.example.myapplication.authorization.data.network

import android.util.Log
import com.example.myapplication.authorization.data.dto.registration.RegistrationRequest
import com.example.myapplication.authorization.data.dto.Response
import com.example.myapplication.authorization.data.dto.login.LoginRequest
import com.example.myapplication.authorization.data.dto.login.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthRetrofitNetworkClient(private val retrofitNetworkClient: AuthRetrofitClient) :
    AuthNetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return try {
            if (dto is RegistrationRequest) {
                withContext(Dispatchers.IO) {
                    val response = retrofitNetworkClient.api.registerUser(dto)
                    response.resultCode = 200
                    response
                }
            }
            else {
                Log.d("AUTH", "Ошибка валидации")
                Response().apply {
                    resultCode = 400
                }
            }
        }
        catch (e: HttpException) {
            Log.d("AUTH", "Ошибка сервера - ${e.message}")
            Response().apply {
                resultCode = e.code()
            }
        }
    }

    override suspend fun doLoginRequest(dto: Any): Response {
        return try {
            if (dto is LoginRequest) {
                withContext(Dispatchers.IO) {
                    val response = retrofitNetworkClient.api.loginUser(dto)
                    response.apply {
                        resultCode = 200
                    }
                }
            }
            else {
                Log.d("LOGIN", "Ошибка валидации")
                Response().apply {
                    resultCode = 400
                }
            }
        }
        catch (e: HttpException) {
            Log.d("LOGIN", "Ошибка сервера - ${e.message}")
            Response().apply {
                resultCode = e.code()
            }
        }
    }
}
