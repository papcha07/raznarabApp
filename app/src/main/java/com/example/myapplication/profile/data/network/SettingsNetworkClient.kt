package com.example.myapplication.profile.data.network

import com.example.myapplication.authorization.data.dto.Response
import com.example.myapplication.profile.data.model.TokenRequest
import com.example.myapplication.profile.data.model.UserInfoRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SettingsNetworkClient(private val retrofitInstance: ProfileRetrofitInstance) :
    SettingsNetworkClientInterface {

    override suspend fun doRequestInfo(dto: Any): Response {
        return try {
            if (dto is TokenRequest) {
                withContext(Dispatchers.IO) {
                    val response = ProfileRetrofitInstance.userApi.getClientInfo(dto.userId, "Bearer ${dto.token}")
                    response.resultCode = 200
                    response
                }
            } else {
                Response().apply {
                    resultCode = 400
                }
            }
        } catch (e: HttpException) {
            val responseCode = e.code()
            Response().apply {
                resultCode = responseCode
            }
        }
    }

    override suspend fun updateInfoRequest(dto1: Any, dto2: Any): Response {
        return try {

            if(dto2 is UserInfoRequest && dto1 is TokenRequest) {
                withContext(Dispatchers.IO){
                    val response = retrofitInstance.updateUserApi.updateUserInfo(
                        dto1.userId,
                        "Bearer ${dto1.token}",
                        dto2
                    )
                    response.resultCode = 200
                    response
                }
            }
            else{
                Response().apply {
                    resultCode = 400
                }
            }
        }
        catch (e: HttpException){
            Response().apply {
                resultCode = e.code()
            }
        }
    }

}