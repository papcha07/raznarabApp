package com.example.myapplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.di.appModule
import com.example.di.dataModule
import com.example.di.domainModule
import com.example.di.viewModelModule
import com.example.myapplication.settings.domain.api.ThemeInteractor
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.definition.indexKey

class App() : Application() {

    private val themeInteractor: ThemeInteractor by inject()
    private var theme = false


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule,dataModule, domainModule, viewModelModule))
        }
        val shared = themeInteractor.getShared()

        if(!shared.contains(CONST.THEME_SHARED_PREF)){
            theme = false // для понимания
            themeInteractor.switchTheme(theme)
        }
        else{
            theme = themeInteractor.getTheme() //получили значение
            switchTheme(theme)
        }




    }

    public fun switchTheme(isDark : Boolean){
        theme = isDark
        AppCompatDelegate.setDefaultNightMode(
            if(theme) {
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        themeInteractor.switchTheme(theme)
    }







}