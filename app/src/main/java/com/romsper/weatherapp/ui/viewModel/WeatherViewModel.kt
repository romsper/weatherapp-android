package com.romsper.weatherapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import com.romsper.weatherapp.R
import com.romsper.weatherapp.data.model.WeatherModel
import com.romsper.weatherapp.data.api.ApiFactory
import com.romsper.weatherapp.data.api.BaseViewModel
import retrofit2.Response

class WeatherViewModel: BaseViewModel() {
    val weatherModel = MutableLiveData<Response<WeatherModel>>()
    private lateinit var weather: Response<WeatherModel>

    fun getWeather(city: String, units: String) {
        doDefault {
            weather = ApiFactory.corWeatherApi.getWeather(city = city, units = units).await()
            weatherModel.postValue(weather)
        }
    }

    fun getWeatherIcon() = when (weather.body()!!.weather.first().main) {
        "Thunderstorm" -> R.drawable.ic_thunder
        "Drizzle", "Rain" -> R.drawable.ic_rainy
        "Snow" -> R.drawable.ic_snowy
        "Clouds" -> R.drawable.ic_cloudy
        "Clear" -> R.drawable.ic_sunny
        else -> null
    }
}
