package com.farid.weatherlogger.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farid.weatherlogger.BuildConfig
import com.farid.weatherlogger.model.response.WeatherResponse
import com.farid.weatherlogger.network.weatherrepo.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(var weatherRepository: WeatherRepository) : ViewModel() {

    fun getCurrentWeatherByLatLong(lat: String, lon: String): LiveData<WeatherResponse> {
        return weatherRepository.getCurrentWeatherByLatLong(
            lat,
            lon,
            BuildConfig.WEATHER_APP_ID,
            "metric"
        )
    }

    fun insertWeatherDB(weatherResponse: WeatherResponse) = viewModelScope.launch {
        weatherRepository.insertWeatherDB(weatherResponse)
    }

     fun selectWeatherDB(): LiveData<List<WeatherResponse>> {
        return weatherRepository.selectWeatherDB()
    }
}