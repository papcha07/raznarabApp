package com.example.myapplication.authorization.data.dto.registration

data class CompanyRegistrationRequest(
    val email: String,
    val password: String,
    val companyName: String,
    val phoneNumber: String
)