package com.example.myapplication.profile.data.local

import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.Nullable
import com.example.myapplication.CONST
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.profile.domain.api.LocalProfileRepository
import com.example.myapplication.profile.domain.model.UserSettingsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LocalProfileRepositoryImpl(
    private val userSharedPreferences: SharedPreferences
) : LocalProfileRepository {


    override  fun saveUserData(userData: String) {
        userSharedPreferences
            .edit()
            .putString(CONST.USER_DATA_SHARED_PREF, userData)
            .apply()
    }

    override fun getUserData(): Flow<Resource<String?>> = flow {
        withContext(Dispatchers.IO) {
            val currentUserDate = userSharedPreferences.getString(CONST.USER_DATA_SHARED_PREF, null)

            when (currentUserDate) {
                null -> {
                    emit(Resource.Failed("Ошибка получения данных с локального хранилища"))
                    Log.d("local_storage", "Ошибка получения данных с локального хранилища")
                }

                else -> emit(Resource.Success(currentUserDate))
            }
        }
    }
}