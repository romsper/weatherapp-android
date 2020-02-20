package com.romsper.weatherapp.screen.corutines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.network.ApiFactory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ForecastViewModel : ViewModel() {
    private val repository : ForecastRepository = ForecastRepository(ApiFactory.corWeatherApi)
    val forecastModel = MutableLiveData<ForecastModel>()

    fun fetchForecast(city: String){
        CoroutineScope(Dispatchers.Default).launch {
            val forecast = repository.getForecast(city)
            forecastModel.postValue(forecast)
        }
    }
}