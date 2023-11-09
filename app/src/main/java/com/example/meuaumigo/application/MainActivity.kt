package com.example.meuaumigo.application

import android.app.Application
import org.koin.core.context.startKoin

class MainActivity : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(networkModule, appModule)
        }
    }

}