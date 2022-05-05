package com.example.bookfinderapp.di

import com.example.bookfinderapp.domain.usecase.GetBookDataUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetBookDataUseCase(get()) }
}