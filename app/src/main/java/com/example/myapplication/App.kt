package com.example.myapplication

import android.app.Application
import com.example.di.appModule
import com.example.di.dataModule
import com.example.di.domainModule
import com.example.di.viewModelModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App() : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule,dataModule, domainModule, viewModelModule))
        }

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("myrealm.realm")
            .schemaVersion(1)
            .migration{
                realm, oldVersion, newVersion ->
                {

                }
            }
            .build()

        Realm.setDefaultConfiguration(config)
    }
}