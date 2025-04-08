package com.example.myapplication.authorization.domain.api

import com.example.myapplication.authorization.domain.model.User
import com.example.myapplication.authorization.ui.model.Category
import kotlinx.coroutines.flow.Flow

interface AuthInteractor {

    fun registration(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        patronymic: String,
        phone: String,
        role: Category
    ) : Flow<Pair<User?, String?>>

    fun login(email: String, password: String) : Flow<Pair<String?, String?>>
}