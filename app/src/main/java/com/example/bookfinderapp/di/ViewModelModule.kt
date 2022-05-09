package com.example.bookfinderapp.di

import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import com.example.bookfinderapp.ui.detail.BookDetailViewModel
import com.example.bookfinderapp.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(androidApplication(), get(), get(), get()) }
    viewModel { (bookMarkEntity: BookMarkEntity) -> BookDetailViewModel(androidApplication(), bookMarkEntity, get(), get(), get()) }
}
