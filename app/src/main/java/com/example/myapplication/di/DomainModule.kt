    package com.example.di


    import com.example.myapplication.authorization.domain.api.AuthInteractor
    import com.example.myapplication.authorization.domain.AuthInteractorImpl
    import com.example.myapplication.order.domain.api.MapInteractorInterface
    import com.example.myapplication.order.domain.interactor.MapInteractor
    import com.example.myapplication.profile.domain.LocalDataUseCase
    import com.example.myapplication.profile.domain.UserInfoUseCase
    import com.example.myapplication.profile.domain.api.LocalDataInteractorInterface
    import com.example.myapplication.profile.domain.api.UserInfoUseCaseInterface
    import com.example.myapplication.raznarab.ui.domain.api.CoordinatesInteractor
    import com.example.myapplication.raznarab.ui.domain.impl.CoordinatesInteractorImpl
    import com.example.myapplication.settings.domain.ThemeInteractorImpl
    import com.example.myapplication.settings.domain.api.ThemeInteractor
    import com.example.myapplication.sharing.ShareInteractor
    import com.example.myapplication.sharing.ShareInteractorImpl
    import com.example.myapplication.sharing.ShareRepository
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

        single<LocalDataInteractorInterface>{
            LocalDataUseCase(get(), get())
        }
        single<ThemeInteractor>{
            ThemeInteractorImpl(get())
        }

        single<ShareInteractor>{
            ShareInteractorImpl(get())
        }

        single<CoordinatesInteractor>{
            CoordinatesInteractorImpl(get())
        }





    }