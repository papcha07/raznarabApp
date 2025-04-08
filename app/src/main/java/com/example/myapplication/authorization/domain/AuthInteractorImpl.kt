package com.example.myapplication.authorization.domain

import android.util.Log
import com.example.myapplication.authorization.domain.api.AuthInteractor
import com.example.myapplication.authorization.domain.api.AuthRepository
import com.example.myapplication.authorization.domain.model.Resource
import com.example.myapplication.authorization.domain.model.User
import com.example.myapplication.authorization.ui.model.Category
import com.example.myapplication.token.data.TokenEncryptedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthInteractorImpl(
    val authRepository: AuthRepository,
    val tokenRepository : TokenEncryptedRepository
) : AuthInteractor {

    override fun registration(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        patronymic: String,
        phone: String,
        role: Category
    ): Flow<Pair<User?, String?>> {
        return authRepository.registerUser(email, password, firstName, lastName, patronymic, phone)
            .map { result ->
                when (result) {
                    is Resource.Success ->{
                        Pair(User(result.id, email, password, firstName, lastName, patronymic, phone, role), null)
                    }

                    is Resource.Failed -> Pair(null, result.message)
                }
            }
    }

    override fun login(email: String, password: String): Flow<Pair<String?, String?>> {
        return authRepository.loginUser(email, password).map { result ->

            when (result) {
                is Resource.Success -> {
                    val token = result.id
                    Pair(token, null)
                }

                is Resource.Failed -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
