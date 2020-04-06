package com.romsper.weatherapp.fragment

import androidx.lifecycle.MutableLiveData
import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.network.ApiFactory
import com.romsper.weatherapp.network.BaseViewModel
import retrofit2.Response

class ForecastViewModel : BaseViewModel() {
    val forecastModel = MutableLiveData<Response<ForecastModel>>()

    fun getForecast(city: String, units: String) {
        doDefault {
            val result = ApiFactory.corWeatherApi.getForecast(city = city, units = units).await()
            forecastModel.postValue(result)
        }
    }
}
