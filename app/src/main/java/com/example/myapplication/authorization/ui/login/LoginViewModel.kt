package com.example.myapplication.authorization.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.authorization.domain.api.AuthInteractor
import com.example.myapplication.token.domain.TokenInteractor
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authInteractor: AuthInteractor,
    private val tokenInteractor: TokenInteractor
    ) : ViewModel() {
    private val loginState = MutableLiveData<LoginScreenState>()
    fun getLoginState(): LiveData<LoginScreenState> {
        return loginState
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authInteractor.login(email, password).collect { pair ->
                val token = pair.first
                val message = pair.second

                if (token == null) {
                    loginState.postValue(LoginScreenState.Error(message!!))
                }
                else {
                    loginState.postValue(LoginScreenState.Success(token))
                    tokenInteractor.saveToken(token)
                }
            }
        }
    }
}