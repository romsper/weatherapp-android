package com.romsper.weatherapp.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.romsper.weatherapp.model.ForecastModel
import com.romsper.weatherapp.R
import com.romsper.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainActivityView {
    lateinit var binding: ActivityMainBinding
    lateinit var button: Button
    lateinit var inpCity: EditText
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenter = MainActivityPresenter(this)

        inpCity = findViewById(R.id.inp_city)
        button = findViewById(R.id.get_forecast)
        button.setOnClickListener {
            presenter.loadData(inpCity.text.toString())
        }
    }

    override fun setData(model: ForecastModel) {
        binding.weather.text = model.list.first().weather.first().main
    }
}