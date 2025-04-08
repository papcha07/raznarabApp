package com.example.myapplication.authorization.ui.registration

import com.example.myapplication.authorization.domain.model.User

sealed interface RegistrationScreenState {
    data class Success(val user: User) : RegistrationScreenState
    object AlreadyExist : RegistrationScreenState
    object Error : RegistrationScreenState
}
