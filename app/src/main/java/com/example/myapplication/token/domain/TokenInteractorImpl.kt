package com.example.myapplication.token.domain

import com.example.myapplication.token.data.TokenEncryptedRepository

class TokenInteractorImpl(private val tokenEncryptedRepository: TokenEncryptedRepository) : TokenInteractor {

    override suspend fun saveToken(token: String) {
        tokenEncryptedRepository.saveToken(token)
    }

    override fun getToken(): String? {
        return tokenEncryptedRepository.getToken()
    }

    override fun getUserId(): String? {
        return tokenEncryptedRepository.getUserId()
    }

}