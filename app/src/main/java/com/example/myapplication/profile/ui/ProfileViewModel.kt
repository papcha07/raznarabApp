package com.example.myapplication.profile.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.domain.api.LocalDataInteractorInterface
import com.example.myapplication.profile.domain.api.UserInfoUseCaseInterface
import com.example.myapplication.profile.domain.model.UserInfoRequest
import com.example.myapplication.sharing.ShareInteractor
import com.example.myapplication.token.domain.TokenInteractor
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInfoUseCaseInterface: UserInfoUseCaseInterface,
    private val tokenInteractor: TokenInteractor,
    private val shareInteractor: ShareInteractor
) : ViewModel() {

    private val infoState = MutableLiveData<ProfileInfoStateScreen>()
    fun getInfoState(): LiveData<ProfileInfoStateScreen> = infoState

    val uriState = MutableLiveData<Uri?>()



    init {
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
                when {
                    userInfo == null -> {
                        if (message == "Ошибка подключения") {
                        } else {
                            infoState.postValue(ProfileInfoStateScreen.Error(message!!))
                        }
                    }
                    else -> {
                        infoState.postValue(ProfileInfoStateScreen.Content(userInfo))
                    }
                }
            }
        }
    }


    private val executorInfoState = MutableLiveData<ProfileInfoStateScreen>()
    fun getExecutorInfoState(): LiveData<ProfileInfoStateScreen> = executorInfoState

    fun loadInfoByExecutor(executorId: String){
        val token = getToken()
        viewModelScope.launch {
            userInfoUseCaseInterface.getUserInfo(executorId, token).collect{
                pair ->
                val userInfo = pair.first
                val message = pair.second
                when{
                    userInfo == null -> {
                        if (message == "Ошибка подключения") {
                        } else {
                            executorInfoState.postValue(ProfileInfoStateScreen.Error(message!!))
                        }
                    }
                    else -> {
                        executorInfoState.postValue(ProfileInfoStateScreen.Content(userInfo))
                    }
                }
            }
        }
    }

    fun updateInfo(userInfo: UserInfoRequest) {

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

    fun messageToAdmins() {
        shareInteractor.messageToSupport()
    }


}