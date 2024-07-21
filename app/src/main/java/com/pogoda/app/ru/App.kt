package com.pogoda.app.ru

import android.app.Application
import android.util.Log
import com.pogoda.app.ru.di.appKoinModule
import com.pogoda.app.ru.settings.MAIN_LOG_TAG
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(MAIN_LOG_TAG, "App Launched!")
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appKoinModule)
        }
    }
}