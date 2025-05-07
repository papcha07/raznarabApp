package com.example.myapplication.token.data

import androidx.security.crypto.EncryptedSharedPreferences
import com.auth0.jwt.JWT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenEncryptedRepositoryImpl(
    private val encryptedSharedPreferences: EncryptedSharedPreferences
) : TokenEncryptedRepository {

    override suspend fun saveToken(token: String) {
        withContext(Dispatchers.IO){
            encryptedSharedPreferences.edit()
                .putString("AUTH_TOKEN", token)
                .apply()
        }
    }

    override fun getToken(): String? {
        return encryptedSharedPreferences.getString("AUTH_TOKEN", null)
    }

    override fun getUserId(): String {
        val token = getToken()
        val decodedJWT = JWT.decode(token)
        return decodedJWT.getClaim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier").asString()
    }

}