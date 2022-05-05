package com.example.bookfinderapp.di

import com.example.bookfinderapp.data.repository.Repository
import com.example.bookfinderapp.data.repository.RepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<Repository> {  RepositoryImpl(get()) }
}