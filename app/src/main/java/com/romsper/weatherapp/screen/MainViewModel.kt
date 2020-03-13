package com.romsper.weatherapp.screen

import androidx.lifecycle.MutableLiveData
import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.network.ApiFactory
import com.romsper.weatherapp.screen.corutines.BaseViewModel

class MainViewModel : BaseViewModel() {
    val forecastModel = MutableLiveData<ForecastModel>()

    fun getWeather(city: String) {
        doDefault {
            val result = ApiFactory.corWeatherApi.getForecast(city).await().body()
            forecastModel.postValue(result) //Set result to LiveData
        }
    }
}