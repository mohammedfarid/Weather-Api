package com.farid.weatherlogger.network.weatherrepo

import android.app.Application
import androidx.lifecycle.LiveData
import com.farid.weatherlogger.model.response.WeatherResponse
import com.farid.weatherlogger.network.BaseRepository
import com.farid.weatherlogger.network.daos.WeatherDAO

class WeatherRepository(weatherInterface: WeatherInterface, var weatherDAO: WeatherDAO,application: Application) :
    BaseRepository<WeatherInterface>(weatherInterface,application) {
    fun getCurrentWeatherByLatLong(
        lat: String,
        lon: String,
        appId: String,
        units: String
    ): LiveData<WeatherResponse> {
        return fetchData { service.currentWeatherApi(lat, lon, appId, units) }
    }

    suspend fun insertWeatherDB(weatherResponse: WeatherResponse) {
        weatherDAO.insertWeather(weatherResponse)
    }

     fun selectWeatherDB(): LiveData<List<WeatherResponse>> {
        return weatherDAO.selectWeather()
    }
}