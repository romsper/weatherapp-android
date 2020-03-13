package com.romsper.weatherapp.api

import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.BuildConfig
import com.romsper.weatherapp.model.WeatherModel
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeather {

    @GET("forecast/")
    fun getRxForecast(@Query("q") city: String,
                      @Query("units") units: String = "metric",
                      @Query("appid") appid: String = BuildConfig.API_KEY) : Observable<ForecastModel>

    @GET("forecast/")
    fun getForecast(@Query("q") city: String,
                    @Query("units") units: String = "metric",
                    @Query("appid") appid: String = BuildConfig.API_KEY) : Deferred<Response<ForecastModel>>

    @GET("weather/")
    fun getWeather(@Query("q") city: String,
                   @Query("units") units: String = "metric",
                   @Query("appid") appid: String = BuildConfig.API_KEY) : Deferred<Response<WeatherModel>>
}