package com.example.meuaumigo.application

import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module{
    factory {
        Retrofit.Builder()
            .baseUrl("")
            .build()
    }
}