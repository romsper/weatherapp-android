package com.romsper.weatherapp.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.romsper.weatherapp.R
import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.screen.corutines.ForecastViewModel
import com.romsper.weatherapp.screen.rxjava.MainActivityPresenter
import com.romsper.weatherapp.screen.rxjava.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.backlayer.*
import kotlinx.android.synthetic.main.frontlayer.*


class MainActivity : AppCompatActivity(), MainActivityView {
    lateinit var rxPresenter: MainActivityPresenter
    lateinit var corViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rxPresenter = MainActivityPresenter(this)
        corViewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)

        btn_get_rx_forecast.setOnClickListener {
            rxPresenter.loadData(inp_city.text.toString())
        }

        btn_get_cor_forecast.setOnClickListener {
            displayForecast()
        }
    }

    fun displayForecast() {
        corViewModel.fetchForecast(inp_city.text.toString())
        corViewModel.forecastModel.observe(this, Observer {
            backdrop_container.open()
            txt_weather.text = it.list.first().weather.first().main
        })
    }

    override fun setData(model: ForecastModel) {
        backdrop_container.open()
        txt_weather.text = model.list.first().weather.first().main
    }
}