package com.example.myapplication.authorization.data.dto.login

import com.example.myapplication.authorization.data.dto.Response

data class LoginResponse(
    val token: String
) : Response()