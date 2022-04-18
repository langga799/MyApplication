package com.langga.movieapp

import android.app.Application
import com.langga.movieapp.core.di.databaseModule
import com.langga.movieapp.core.di.networkModule
import com.langga.movieapp.core.di.repositoryModule
import com.langga.movieapp.di.useCaseModule
import com.langga.movieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    useCaseModule,
                    viewModelModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                )
            )
        }
    }
}