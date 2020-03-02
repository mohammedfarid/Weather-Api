package com.farid.weatherlogger.network.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farid.weatherlogger.model.response.WeatherResponse
import com.farid.weatherlogger.network.daos.WeatherDAO

@Database(entities = [WeatherResponse::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO
}