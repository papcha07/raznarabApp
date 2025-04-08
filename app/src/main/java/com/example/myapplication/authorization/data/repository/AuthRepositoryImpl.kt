package com.example.myapplication.authorization.data.repository

import android.util.Log
import com.example.myapplication.authorization.data.dto.login.LoginRequest
import com.example.myapplication.authorization.data.dto.login.LoginResponse
import com.example.myapplication.authorization.domain.api.AuthRepository
import com.example.myapplication.authorization.domain.model.Resource
import com.example.myapplication.authorization.data.dto.registration.RegistrationRequest
import com.example.myapplication.authorization.data.dto.registration.RegistrationResponse
import com.example.myapplication.authorization.data.network.AuthNetworkClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(private val authNetworkClient: AuthNetworkClient) : AuthRepository {

    override fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        patronymic: String,
        phone: String
    ): Flow<Resource<String>> = flow {
        val codeResponse = authNetworkClient.doRequest(
            RegistrationRequest(email, password, firstName, lastName, patronymic, phone)
        )

        when (codeResponse.resultCode) {
            200 -> {
                val id = (codeResponse as RegistrationResponse).userId
                Log.d("IDUSER", id)
                emit(Resource.Success(id))
            }
            400 -> {
                emit(Resource.Failed("Ошибка валидации"))
            }

            401 -> {
                emit(Resource.Failed("Такой аккаунт уже зарегистрирован!"))
            }
            500 -> {
                emit(Resource.Failed("Проблемы с сервером"))
            }
        }
    }

    override fun loginUser(email: String, password: String): Flow<Resource<String>> = flow{
        val codeResponse = authNetworkClient.doLoginRequest(LoginRequest(email, password))
        when(codeResponse.resultCode){

            200 -> {
                val token = (codeResponse as LoginResponse).token
                emit(Resource.Success(token))
            }

            400 ->{emit(Resource.Failed("Ошибка валидации"))}

            401 -> {emit(Resource.Failed("Неверный логин или пароль"))}

            500 -> {emit(Resource.Failed("Ошибка сервера"))}

        }
    }
}
