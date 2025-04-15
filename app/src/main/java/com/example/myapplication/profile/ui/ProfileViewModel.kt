package com.example.myapplication.profile.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.domain.LocalDataUseCase
import com.example.myapplication.profile.domain.api.UserInfoUseCaseInterface
import com.example.myapplication.profile.domain.model.UserSettingsModel
import com.example.myapplication.token.domain.TokenInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInfoUseCaseInterface: UserInfoUseCaseInterface,
    private val tokenInteractor: TokenInteractor
) : ViewModel() {

    init {
        loadInfo()
    }

    private val infoState = MutableLiveData<ProfileInfoStateScreen>()
    fun getInfoState(): LiveData<ProfileInfoStateScreen> = infoState

    private fun getToken(): String = tokenInteractor.getToken()!!
    private fun getUserId(): String = tokenInteractor.getUserId()!!

    fun loadInfo() {
        val token = getToken()
        val id = getUserId()
        viewModelScope.launch {
            userInfoUseCaseInterface.getUserInfo(id, token).collect { pair ->
                val userInfo = pair.first
                val message = pair.second
                when {
                    userInfo == null -> {
                        infoState.postValue(ProfileInfoStateScreen.Error(message!!))
                    }

                    else -> {
                        infoState.postValue(ProfileInfoStateScreen.Content(userInfo))
                    }
                }
            }
        }
    }

    fun updateInfo(userInfo: UserSettingsModel) {

        val id = getUserId()
        val token = getToken()

        viewModelScope.launch {
            userInfoUseCaseInterface.updateUserInfo(id, token, userInfo).collect { pair ->
                val userInfo = pair.first
                val message = pair.second
                when {
                    userInfo == null -> {
                        infoState.postValue(ProfileInfoStateScreen.Error(message!!))
                    }

                    else -> {
                        loadInfo()
                    }
                }
            }
        }
    }

}