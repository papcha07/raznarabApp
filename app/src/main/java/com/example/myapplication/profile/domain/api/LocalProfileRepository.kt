package com.example.myapplication.profile.domain.api

import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.profile.domain.model.UserSettingsModel
import kotlinx.coroutines.flow.Flow

interface LocalProfileRepository {

     fun saveUserData(userData: String)
     fun getUserData() : Flow<Resource<String?>>
}
