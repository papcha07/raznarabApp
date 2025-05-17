package com.example.myapplication.profile.domain

import android.util.Log
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.profile.domain.api.SettingsRepositoryInterface
import com.example.myapplication.profile.domain.api.UserInfoUseCaseInterface
import com.example.myapplication.profile.domain.model.UserSettingsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserInfoUseCase(private val settingsRepository: SettingsRepositoryInterface) :
    UserInfoUseCaseInterface {

    override fun getUserInfo(userId: String ,token: String): Flow<Pair<UserSettingsModel?, String?>>{
        return settingsRepository.getUserInfo(userId, token).map {
            result ->
            when(result){
                is Resource.Success -> {
                    Log.d("RESULT",
                        "${result.data.email} " +
                                "${result.data.firstName}")
                    Pair(result.data, null)
                }

                is Resource.Failed -> {
                    Pair(null ,result.message)
                }
            }
        }
    }

    override fun updateUserInfo(
        userId: String,
        token: String,
        userInfo: UserSettingsModel
    ): Flow<Pair<UserSettingsModel?, String?>> {
        return settingsRepository.updateUserInfo(userId, token, userInfo).map {
            result ->

            when(result){
                is Resource.Success -> {
                    Log.d("RESULT",
                        "${result.data.email} " +
                                "${result.data.firstName}")
                    Pair(
                        UserSettingsModel(
                            email = result.data.email,
                            phoneNumber = result.data.phoneNumber,
                            firstName = result.data.firstName,
                            secondName = result.data.secondName,
                            patronymic = result.data.patronymic,
                            description = result.data.description,
                            rating = 1,
                            avatarPath = ""
                        ), null)
                }
                is Resource.Failed -> {
                    Pair(null ,result.message)
                }
            }
        }
    }
}