package com.example.myapplication.authorization.domain.api

import com.example.myapplication.authorization.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        patronymic: String,
        phone: String
    ) : Flow<Resource<String>>

    fun loginUser(email: String, password: String) : Flow<Resource<String>>
}
