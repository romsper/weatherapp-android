package com.romsper.weatherapp.data.repository

import com.romsper.weatherapp.data.api.ApiWeather
import com.romsper.weatherapp.data.model.WeatherModel

class WeatherRepository(private val api: ApiWeather): BaseRepository() {

    suspend fun getWeather(city: String): WeatherModel? {
        return safeApiCall(
        call = { api.getWeather(city).await() },
        errorMessage = "Error")
    }
}