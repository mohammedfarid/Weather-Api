package com.farid.weatherlogger.network.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.farid.weatherlogger.model.response.WeatherResponse

@Dao
interface WeatherDAO {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(vararg weatherResponse: WeatherResponse)

    @Transaction
    @Query("SELECT * FROM weather_table")
     fun selectWeather(): LiveData<List<WeatherResponse>>
}