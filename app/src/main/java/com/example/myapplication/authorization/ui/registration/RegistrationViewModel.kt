package com.example.myapplication.authorization.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.authorization.domain.api.AuthInteractor
import com.example.myapplication.authorization.ui.model.Category
import kotlinx.coroutines.launch

class RegistrationViewModel(private val authInteractor: AuthInteractor) : ViewModel() {

    private val screenState = MutableLiveData<Category>()
    fun getScreenState(): LiveData<Category> = screenState

    private val registrationScreenState = MutableLiveData<RegistrationScreenState>()
    fun getRegistrationScreenState(): LiveData<RegistrationScreenState> = registrationScreenState


    fun chooseRoleState(category: Category) {
        screenState.postValue(category)
    }

    fun registration(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        patronymic: String,
        phone: String,
        role: Category
    ) {
        viewModelScope.launch {
            authInteractor.registration(email, password, firstName, lastName, patronymic, phone, role).collect {
                pair ->
                val user = pair.first
                val message = pair.second

                when{
                    user != null -> {
                        registrationScreenState.postValue(RegistrationScreenState.Success(user))
                    }

                    message == "Такой аккаунт уже зарегистрирован!" -> {
                        registrationScreenState.postValue(RegistrationScreenState.AlreadyExist)
                    }

                    else -> {
                        registrationScreenState.postValue(RegistrationScreenState.Error)
                    }

                }
            }
        }
    }


}