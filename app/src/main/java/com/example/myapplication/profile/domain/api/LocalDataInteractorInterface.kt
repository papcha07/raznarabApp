package com.example.myapplication.profile.domain.api

import com.example.myapplication.profile.domain.model.UserSettingsModel
import kotlinx.coroutines.flow.Flow

interface LocalDataInteractorInterface {

    fun saveUserData(userSettingsModel: UserSettingsModel)
    suspend fun getUserData() : Flow<Pair<UserSettingsModel?, String?>>
}