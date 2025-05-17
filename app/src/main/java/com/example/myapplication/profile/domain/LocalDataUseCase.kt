package com.example.myapplication.profile.domain

import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.profile.domain.api.LocalDataInteractorInterface
import com.example.myapplication.profile.domain.api.LocalProfileRepository
import com.example.myapplication.profile.ui.UserSettingsModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataUseCase(
    private val localProfileRepository: LocalProfileRepository,
    private val gson: Gson
) : LocalDataInteractorInterface {

    override fun saveUserData(userSettingsModel: UserSettingsModel) {
        val modelToString = gson.toJson(userSettingsModel)
        localProfileRepository.saveUserData(modelToString)
    }

    override suspend fun getUserData(): Flow<Pair<UserSettingsModel?, String?>> {
        return localProfileRepository.getUserData().map { resourse ->
            when (resourse) {
                is Resource.Success -> {
                    Pair(gson.fromJson(resourse.data, UserSettingsModel::class.java), null)
                }

                is Resource.Failed -> {
                    Pair(null, resourse.message)
                }
            }
        }
    }
}