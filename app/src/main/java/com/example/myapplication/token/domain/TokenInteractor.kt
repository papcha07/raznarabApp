package com.example.myapplication.token.domain

interface TokenInteractor {
    suspend fun saveToken(token: String)
    fun getToken() : String?
    fun getUserId() : String?
}