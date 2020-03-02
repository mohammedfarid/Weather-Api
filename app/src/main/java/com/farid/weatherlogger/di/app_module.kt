package com.farid.weatherlogger.di

import androidx.room.Room
import com.farid.weatherlogger.network.database.WeatherDatabase
import com.farid.weatherlogger.network.weatherrepo.WeatherInterface
import com.farid.weatherlogger.network.weatherrepo.WeatherRepository
import com.farid.weatherlogger.viewModel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dbModule = module {
    single { Room.databaseBuilder(get(), WeatherDatabase::class.java, "weathers_database").build() }

    single { get<WeatherDatabase>().weatherDao() }
}
val networkModule = module {
    single { WeatherInterface.create() }
}
val repositoryModule = module {
    single { WeatherRepository(get(), get(),get()) }
}
val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
}