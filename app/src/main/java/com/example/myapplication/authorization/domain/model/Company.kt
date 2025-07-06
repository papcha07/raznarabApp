package com.example.myapplication.authorization.domain.model

import com.mobsandgeeks.saripaar.annotation.Email

data class Company(
    val id: String?,
    val email: String,
    val password: String,
    val companyName: String,
    val phoneNumber: String
)
