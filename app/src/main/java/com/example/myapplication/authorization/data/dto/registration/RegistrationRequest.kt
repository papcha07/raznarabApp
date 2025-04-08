package com.example.myapplication.authorization.data.dto.registration

data class RegistrationRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val secondName: String,
    val patronymic: String,
    val phoneNumber: String,
)