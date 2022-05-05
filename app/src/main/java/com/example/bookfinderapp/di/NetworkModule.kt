package com.example.bookfinderapp.di

import com.example.bookfinderapp.data.network.buildOkHttpClient
import com.example.bookfinderapp.data.network.provideBookFinderApiService
import com.example.bookfinderapp.data.network.provideBookFinderRetrofit
import com.example.bookfinderapp.data.network.provideGsonConverterFactory
import org.koin.dsl.module

val networkModule = module {
    single { provideGsonConverterFactory() }

    single { buildOkHttpClient() }

    single { provideBookFinderRetrofit(get(), get()) }

    single { provideBookFinderApiService(get()) }
}