package com.example.bookfinderapp.di

import com.example.bookfinderapp.data.db.provideBookMarkDao
import com.example.bookfinderapp.data.db.provideDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataBaseModule = module {
    single { provideDB(androidApplication()) }
    single { provideBookMarkDao(get()) }
}