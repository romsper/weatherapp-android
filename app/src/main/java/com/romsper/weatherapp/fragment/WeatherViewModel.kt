package com.romsper.weatherapp.fragment

import androidx.lifecycle.MutableLiveData
import com.romsper.weatherapp.model.WeatherModel
import com.romsper.weatherapp.network.ApiFactory
import com.romsper.weatherapp.screen.corutines.BaseViewModel
import retrofit2.Response

class WeatherViewModel: BaseViewModel() {
    val weatherModel = MutableLiveData<Response<WeatherModel>>()

    fun getWeather(city: String, units: String) {
        doDefault {
            val result = ApiFactory.corWeatherApi.getWeather(city = city, units = units).await()
            weatherModel.postValue(result)
        }
    }
}
