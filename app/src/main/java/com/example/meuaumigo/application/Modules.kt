package com.example.meuaumigo.application

import com.example.meuaumigo.viewmodel.FirebaseStorageViewModel
import com.example.meuaumigo.repository.FirebaseStorageRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module{
    factory {
        Retrofit.Builder()
            .baseUrl("")
            .build()
    }
}

val appModule = module {
    factory { FirebaseStorageRepository() }
    factory { FirebaseStorageViewModel() }
}