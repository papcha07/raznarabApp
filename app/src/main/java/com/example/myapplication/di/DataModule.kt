package com.example.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.myapplication.authorization.data.network.AuthNetworkClient
import com.example.myapplication.authorization.data.network.AuthRetrofitClient
import com.example.myapplication.authorization.data.network.AuthRetrofitNetworkClient
import com.example.myapplication.authorization.data.repository.AuthRepositoryImpl
import com.example.myapplication.authorization.domain.api.AuthRepository
import com.example.myapplication.order.data.network.NetworkClient
import com.example.myapplication.order.data.network.RetrofitClient
import com.example.myapplication.order.data.network.RetrofitNetworkClient
import com.example.myapplication.order.data.repository.CoordinatesRepositoryImpl
import com.example.myapplication.order.domain.api.CoordinatesRepository
import com.example.myapplication.profile.data.SettingsRepositoryImpl
import com.example.myapplication.profile.data.network.ProfileRetrofitInstance
import com.example.myapplication.profile.data.network.SettingsNetworkClient
import com.example.myapplication.profile.data.network.SettingsNetworkClientInterface
import com.example.myapplication.profile.domain.api.SettingsRepositoryInterface
import com.example.myapplication.token.data.TokenEncryptedRepository
import com.example.myapplication.token.data.TokenEncryptedRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {

    //поиск
    single<CoordinatesRepository> {
        CoordinatesRepositoryImpl(get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single {
        RetrofitClient
    }

    //auth
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single<AuthNetworkClient> {
        AuthRetrofitNetworkClient(get())
    }

    single {
        AuthRetrofitClient
    }

    //token


    single<TokenEncryptedRepository>{
        TokenEncryptedRepositoryImpl(get())
    }

    single {
        MasterKey.Builder(androidContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    single<SharedPreferences> {
        EncryptedSharedPreferences.create(
            androidContext(),
            "secure_prefs",
            get<MasterKey>(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }
    single<EncryptedSharedPreferences> {
        EncryptedSharedPreferences.create(
            androidContext(),
            "secure_prefs",
            get<MasterKey>(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    //profile
    single<SettingsRepositoryInterface>{
        SettingsRepositoryImpl(get())
    }

    single<SettingsNetworkClientInterface>{
        SettingsNetworkClient(ProfileRetrofitInstance)
    }

}
