    package com.example.di


    import com.example.myapplication.authorization.domain.api.AuthInteractor
    import com.example.myapplication.authorization.domain.AuthInteractorImpl
    import com.example.myapplication.order.domain.api.MapInteractorInterface
    import com.example.myapplication.order.domain.interactor.MapInteractor
    import com.example.myapplication.profile.domain.UserInfoUseCase
    import com.example.myapplication.profile.domain.api.UserInfoUseCaseInterface
    import com.example.myapplication.token.domain.TokenInteractor
    import com.example.myapplication.token.domain.TokenInteractorImpl
    import org.koin.dsl.module

    val domainModule = module {

        single<MapInteractorInterface>{
            MapInteractor(get())
        }

        single<AuthInteractor>{
            AuthInteractorImpl(get(), get())
        }

        single<TokenInteractor> {
            TokenInteractorImpl(get())
        }

        single<UserInfoUseCaseInterface>{
            UserInfoUseCase(get())
        }

    }