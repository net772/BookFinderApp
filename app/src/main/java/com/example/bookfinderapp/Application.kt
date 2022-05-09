package com.example.bookfinderapp

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.example.bookfinderapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application: Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(org.koin.core.logger.Level.ERROR)
            androidContext(this@Application)
            modules(
                coroutineModule,
                networkModule,
                useCaseModule,
                viewModelModule,
                repositoryModule,
                dataBaseModule
            )
        }
    }
}