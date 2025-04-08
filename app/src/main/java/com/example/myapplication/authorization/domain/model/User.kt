package com.example.myapplication.authorization.domain.model

import com.example.myapplication.authorization.ui.model.Category


data class User(
    val id: String?,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val phone: String,
    val role: Category
)