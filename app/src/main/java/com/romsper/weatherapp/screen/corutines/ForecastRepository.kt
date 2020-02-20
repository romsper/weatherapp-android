package com.romsper.weatherapp.screen.corutines

import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.network.ApiWeather
import com.romsper.weatherapp.network.BaseRepository

class ForecastRepository(private val api: ApiWeather) : BaseRepository() {

    suspend fun getForecast(city: String): ForecastModel? {
        val forecast = safeApiCall(
            call = { api.getCorForecast(city).await() },
            errorMessage = "StatusCode: ${api.getCorForecast(city).await().code()}"
        )
        return forecast
    }
}