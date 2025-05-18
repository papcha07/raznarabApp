package com.example.di
import com.example.myapplication.authorization.ui.login.LoginViewModel
import com.example.myapplication.authorization.ui.registration.RegistrationViewModel
import com.example.myapplication.order.ui.placeOrder.OrderViewModel
import com.example.myapplication.profile.ui.ProfileViewModel
import com.example.myapplication.raznarab.ui.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        OrderViewModel(get(), get())
    }

    viewModel {
        RegistrationViewModel(get())
    }

    viewModel {
        LoginViewModel(get(), get())
    }

    viewModel {
        ProfileViewModel(get(), get(), get() , get())
    }

    viewModel{
        MapViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }


}