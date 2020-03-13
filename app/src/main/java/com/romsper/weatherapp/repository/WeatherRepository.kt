package com.romsper.weatherapp.repository

import com.romsper.weatherapp.api.ApiWeather
import com.romsper.weatherapp.model.WeatherModel

class WeatherRepository(private val api: ApiWeather): BaseRepository() {

    suspend fun getWeather(city: String): WeatherModel? {
        return safeApiCall(
        call = { api.getWeather(city).await() },
        errorMessage = "Error Fetching Popular Movies")
    }
}