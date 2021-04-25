package com.romsper.weatherapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import com.romsper.weatherapp.data.model.ForecastModel
import com.romsper.weatherapp.data.api.ApiFactory
import com.romsper.weatherapp.data.api.BaseViewModel
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
