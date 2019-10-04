package com.romsper.weatherapp.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityView {
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)

        btn_get_forecast.setOnClickListener {
            presenter.loadData(inp_city.text.toString())
        }
    }

    override fun setData(model: ForecastModel) {
        txt_weather.text = model.list.first().weather.first().main
    }
}