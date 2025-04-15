package com.example.myapplication.profile.data

import android.util.Log
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.profile.data.model.TokenRequest
import com.example.myapplication.profile.data.model.UserInfoRequest
import com.example.myapplication.profile.data.model.UserInfoResponse
import com.example.myapplication.profile.data.network.SettingsNetworkClientInterface
import com.example.myapplication.profile.domain.api.SettingsRepositoryInterface
import com.example.myapplication.profile.domain.model.UserSettingsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingsRepositoryImpl(private val networkClient: SettingsNetworkClientInterface) :
    SettingsRepositoryInterface {

    override fun getUserInfo(userId: String ,token: String): Flow<Resource<UserInfoResponse>> = flow {
        val resultCode = networkClient.doRequestInfo(TokenRequest(userId, token))
        Log.d("RESULT_CODE_LOAD", resultCode.resultCode.toString())
        when(resultCode.resultCode){

            200 -> {
                val userInfo = (resultCode as UserInfoResponse)
                emit(Resource.Success(userInfo))
            }

            401 -> {
               emit(Resource.Failed("Не авторизирован"))
            }

            404 -> {
                emit(Resource.Failed("Не найден"))
            }

            -1 -> {
                emit(Resource.Failed("Ошибка подключения"))
            }

            else -> {
                emit(Resource.Failed("Ошибка сервера"))
            }

        }
    }

    override fun updateUserInfo(
        userId : String,
        token: String,
        userInfoRequest: UserSettingsModel
    ): Flow<Resource<UserInfoResponse>>  = flow {
        val resultCode = networkClient.updateInfoRequest(
            TokenRequest(userId, token),
            UserInfoRequest(
                email = userInfoRequest.email,
                phoneNumber = userInfoRequest.phoneNumber,
                firstName = userInfoRequest.firstName,
                secondName = userInfoRequest.secondName,
                patronymic = userInfoRequest.patronymic,
                description = userInfoRequest.description
            )
        )
        Log.d("RESULT_CODE_UPDATE", resultCode.toString())
        when(resultCode.resultCode) {
            200 -> {
                val userInfo = (resultCode as UserInfoResponse)
                Log.d("RESPONSE_FROM_SERVER", "${userInfo.toString()}")
                emit(Resource.Success(userInfo))
            }

            401 -> {
                emit(Resource.Failed("Не авторизирован"))
            }

            404 -> {
                emit(Resource.Failed("Не найден"))
            }

            else -> {
                emit(Resource.Failed("Ошибка сервера"))
            }
        }
    }

}