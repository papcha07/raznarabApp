package com.example.myapplication.profile.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.myapplication.authorization.data.dto.Response
import com.example.myapplication.order.data.FileConverter
import com.example.myapplication.profile.data.model.TokenRequest
import com.example.myapplication.profile.domain.model.UserInfoRequest
import com.example.myapplication.profile.data.toMultipartBodyPart
import com.example.myapplication.profile.data.toRequestBodyOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SettingsNetworkClient(
    private val retrofitInstance: ProfileRetrofitInstance,
    private val context: Context
) :
    SettingsNetworkClientInterface {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    override suspend fun doRequestInfo(dto: Any): Response {
        return try {
            when (isInternetAvailable(context)) {
                true -> {
                    if (dto is TokenRequest) {
                        withContext(Dispatchers.IO) {
                            val response = ProfileRetrofitInstance.userApi.getClientInfo(
                                dto.userId,
                                "Bearer ${dto.token}"
                            )
                            response.resultCode = 200
                            response
                        }
                    } else {
                        Response().apply {
                            resultCode = 400
                        }
                    }
                }

                false -> {
                    Response().apply {
                        resultCode = -1
                    }
                }
            }
        } catch (e: HttpException) {
            val responseCode = e.code()
            Response().apply {
                resultCode = responseCode
            }
        }
    }

    override suspend fun updateInfoRequest(
        userId: String,
        token: String,
        model: UserInfoRequest
    ): Response {
        return try {
            when (isInternetAvailable(context)) {
                true -> {
                    withContext(Dispatchers.IO) {
                        val response = retrofitInstance.updateUserApi.updateUserInfo(
                            userId = userId,
                            token = "Bearer ${token}",
                            email = model.email?.toRequestBodyOrNull(),
                            phoneNumber = model.phoneNumber?.toRequestBodyOrNull(),
                            firstName = model.firstName?.toRequestBodyOrNull(),
                            secondName = model.secondName?.toRequestBodyOrNull(),
                            patronymic = model.patronymic?.toRequestBodyOrNull(),
                            description = model.description?.toRequestBodyOrNull(),
                            image = FileConverterProfile.prepareFilePart(context, "Image", model.image),
                            deleteAvatar = model.deleteAvatar?.toRequestBodyOrNull()
                        )
                        response.resultCode = 200
                        response
                    }
                }
                false -> {
                    Response().apply {
                        resultCode = -1
                    }
                }
            }
        } catch (e: HttpException) {
            Response().apply {
                resultCode = e.code()
            }
        }
    }

}