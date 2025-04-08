package com.example.myapplication.authorization.ui.login


sealed interface LoginScreenState {
    data class Success  (val token: String): LoginScreenState
    data class Error(val message: String) : LoginScreenState
}
