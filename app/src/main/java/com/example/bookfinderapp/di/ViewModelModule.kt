package com.example.bookfinderapp.di

import com.example.bookfinderapp.ui.finder.BookFinderViewModel
import com.example.bookfinderapp.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(androidApplication()) }
    viewModel { BookFinderViewModel(androidApplication(), get()) }
}