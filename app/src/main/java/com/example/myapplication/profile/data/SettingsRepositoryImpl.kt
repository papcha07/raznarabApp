package com.example.myapplication.profile.data

import android.util.Log
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.profile.data.model.TokenRequest
import com.example.myapplication.profile.domain.model.UserInfoRequest
import com.example.myapplication.profile.data.model.UserInfoResponse
import com.example.myapplication.profile.data.network.SettingsNetworkClientInterface
import com.example.myapplication.profile.domain.api.SettingsRepositoryInterface
import com.example.myapplication.profile.ui.UserSettingsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingsRepositoryImpl(private val networkClient: SettingsNetworkClientInterface) :
    SettingsRepositoryInterface {

    override fun getUserInfo(userId: String, token: String): Flow<Resource<UserSettingsModel>> =
        flow {
            val resultCode = networkClient.doRequestInfo(TokenRequest(userId, token))
            Log.d("RESULT_CODE_LOAD", resultCode.resultCode.toString())
            when (resultCode.resultCode) {

                200 -> {
                    val userResponse = (resultCode as UserInfoResponse)
                    val userSettingsModel = UserSettingsModel(
                        email = userResponse.email,
                        phoneNumber = userResponse.phoneNumber,
                        rating = userResponse.rating,
                        firstName = userResponse.firstName,
                        secondName = userResponse.secondName,
                        patronymic = userResponse.patronymic,
                        description = userResponse.description,
                        avatarPath = userResponse.avatarPath
                    )
                    emit(Resource.Success(userSettingsModel))
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
        userId: String,
        token: String,
        userInfo: UserInfoRequest
    ): Flow<Resource<UserSettingsModel>> = flow {
        val response = networkClient.updateInfoRequest(userId, token, userInfo)
        when (response.resultCode) {

            200 -> {
                val infoResponse = (response as UserInfoResponse)
                val settingsModel = UserSettingsModel(
                    email = infoResponse.email,
                    phoneNumber = infoResponse.phoneNumber,
                    rating = infoResponse.rating,
                    firstName = infoResponse.firstName,
                    secondName = infoResponse.secondName,
                    patronymic = infoResponse.patronymic,
                    description = infoResponse.description,
                    avatarPath = infoResponse.avatarPath
                )
                emit(Resource.Success(settingsModel))
            }

            -1 -> {
                emit(Resource.Failed("Проблемы с интернетом.."))
            }

            else -> {
                emit(Resource.Failed("Что-то пошло не так.."))
            }

        }
    }


}