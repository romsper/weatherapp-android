package com.romsper.weatherapp.network

import com.romsper.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {
    companion object {
        private fun getClient() =
            OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("appid", BuildConfig.API_KEY)
                    .build()
                chain.proceed(newRequest)
            }.build()

        fun retrofit() =
            Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiWeather::class.java)
    }
}