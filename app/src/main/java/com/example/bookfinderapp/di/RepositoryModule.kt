package com.example.bookfinderapp.di

import com.example.bookfinderapp.data.repository.ApiRepository
import com.example.bookfinderapp.data.repository.ApiRepositoryImpl
import com.example.bookfinderapp.data.repository.DbRepository
import com.example.bookfinderapp.data.repository.DbRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ApiRepository> { ApiRepositoryImpl(get()) }
    single<DbRepository> { DbRepositoryImpl(get() ) }
}