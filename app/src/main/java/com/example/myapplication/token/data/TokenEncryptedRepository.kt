package com.example.myapplication.token.data

interface TokenEncryptedRepository {
    suspend fun saveToken(token: String)
    fun getToken(): String?
    fun getUserId() : String
}
