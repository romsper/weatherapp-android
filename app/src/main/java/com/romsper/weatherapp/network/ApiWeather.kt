package com.romsper.weatherapp.network

import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.BuildConfig
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeather {

    @GET("forecast/")
    fun getRxForecast(@Query("q") city: String,
                      @Query("appid") appid: String = BuildConfig.API_KEY) : Observable<ForecastModel>

    @GET("forecast/")
    fun getCorForecast(@Query("q") city: String,
                       @Query("appid") appid: String = BuildConfig.API_KEY) : Deferred<Response<ForecastModel>>
}