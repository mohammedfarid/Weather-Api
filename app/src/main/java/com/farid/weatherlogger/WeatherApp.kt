package com.farid.weatherlogger

import android.app.Application
import com.farid.weatherlogger.di.dbModule
import com.farid.weatherlogger.di.networkModule
import com.farid.weatherlogger.di.repositoryModule
import com.farid.weatherlogger.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@WeatherApp)
            androidFileProperties()
            // modules
            modules(
                listOf(
                    dbModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}