package com.example.bookfinderapp.di

import com.example.bookfinderapp.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetBookDataUseCase(get()) }
    factory { ReqInsertBookMarkUseCase(get()) }
    factory { ReqDeleteBookMarkUseCase(get()) }
    factory { GetBookMarkUseCase(get()) }
    factory { GetAllBookMarkUseCase(get()) }
}