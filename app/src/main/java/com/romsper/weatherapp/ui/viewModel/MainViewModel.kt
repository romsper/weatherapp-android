package com.romsper.weatherapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import com.romsper.weatherapp.data.model.ForecastModel
import com.romsper.weatherapp.data.api.ApiFactory
import com.romsper.weatherapp.data.api.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val forecastModel = MutableLiveData<ForecastModel>()

    fun getWeather(city: String) {
        doDefault {
            val result = ApiFactory.corWeatherApi.getForecast(city).await().body()
            forecastModel.postValue(result) //Set result to LiveData
        }
    }
}