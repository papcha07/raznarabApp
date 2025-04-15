package com.example.myapplication.profile.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.domain.api.LocalDataInteractorInterface
import com.example.myapplication.profile.domain.api.UserInfoUseCaseInterface
import com.example.myapplication.profile.domain.model.UserSettingsModel
import com.example.myapplication.token.domain.TokenInteractor
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInfoUseCaseInterface: UserInfoUseCaseInterface,
    private val localUseCase: LocalDataInteractorInterface,
    private val tokenInteractor: TokenInteractor
) : ViewModel() {

    private val infoState = MutableLiveData<ProfileInfoStateScreen>()
    fun getInfoState(): LiveData<ProfileInfoStateScreen> = infoState

    init {
        getLocalData()
        loadInfo()
    }

    private fun getToken(): String = tokenInteractor.getToken()!!
    private fun getUserId(): String = tokenInteractor.getUserId()!!

    fun loadInfo() {
        val token = getToken()
        val id = getUserId()
        viewModelScope.launch {
            userInfoUseCaseInterface.getUserInfo(id, token).collect { pair ->
                val userInfo = pair.first
                val message = pair.second

                Log.d("userInfo", "${userInfo.toString()}")
                Log.d("userInfo", "${message}")

                when {
                    userInfo == null -> {
                        if (message == "Ошибка подключения") {
                            getLocalData()
                        } else {
                            infoState.postValue(ProfileInfoStateScreen.Error(message!!))
                        }
                    }

                    else -> {
                        saveLocalData(userInfo)
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

    private fun saveLocalData(user: UserSettingsModel) {
        localUseCase.saveUserData(user)
    }

    private fun getLocalData() {
        viewModelScope.launch {
            localUseCase.getUserData().collect { pair ->
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

}