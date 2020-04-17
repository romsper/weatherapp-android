package com.romsper.weatherapp.screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import com.roacult.backdrop.BackdropLayout
import com.romsper.weatherapp.R
import com.romsper.weatherapp.adapter.ForecastAdapter
import com.romsper.weatherapp.fragment.ForecastViewModel
import com.romsper.weatherapp.fragment.WeatherViewModel
import com.romsper.weatherapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.backlayer.*
import kotlinx.android.synthetic.main.dialog_auth_notification.*
import kotlinx.android.synthetic.main.frontlayer.*
import kotlinx.android.synthetic.main.wether_fragment.*

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var weatherViewModel: WeatherViewModel
    lateinit var forecastViewModel: ForecastViewModel
    lateinit var dialog: Dialog
    private var units: String = ""
    private var symbols: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        forecastViewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)

        dialog = Dialog(this)

        backdropListener()
        switcherListener()
        weatherObserver()
        forecastObserver()
        searchListener()

        if (intent.extras?.get("popup") == true) showDialog()
    }

    private fun showDialog() {
        dialog.setContentView(R.layout.dialog_auth_notification)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.btn_close_dialog.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "No problem! Just enjoy the app!", Toast.LENGTH_SHORT).show() }
        dialog.btn_submit_dialog.setOnClickListener { startActivity(Intent(this, AuthorizationActivity::class.java)) }
        dialog.show()
    }

    private fun backdropListener() = backdrop_container.setOnBackdropChangeStateListener {
        when (it) {
            BackdropLayout.State.OPEN -> {
                txt_weather_title.visibility = View.VISIBLE
            }
            BackdropLayout.State.CLOSE -> {
                txt_weather_title.visibility = View.INVISIBLE
            }
        }
    }

    private fun searchListener() =
        multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener {

            override fun onItemSelected(index: Int, s: CharSequence) {
                weatherViewModel.getWeather(s.toString(), units)
                forecastViewModel.getForecast(s.toString(), units)
                Log.v("Search", "onItemSelected: index: $index, query: $s")
            }

            override fun onTextChanged(index: Int, s: CharSequence) {
                Log.v("Search", "changed: index: $index, query: $s")
            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                weatherViewModel.getWeather(s.toString(), units)
                forecastViewModel.getForecast(s.toString(), units)
                Log.v("Search", "complete: index: $index, query: $s")
            }

            override fun onSearchItemRemoved(index: Int) {
                Log.v("Search", "remove: index: $index")
            }
        })

    fun forecastObserver() = forecastViewModel.forecastModel.observe(this, Observer {
        if (!it.isSuccessful) {
            Toast.makeText(this, "City not found! Please, try again", Toast.LENGTH_SHORT).show()
        } else {
            forecast_recycler.layoutManager = LinearLayoutManager(this)
            forecast_recycler.adapter = ForecastAdapter(it.body()!!.list)
        }
    })

    @SuppressLint("SetTextI18n")
    private fun weatherObserver() = weatherViewModel.weatherModel.observe(this, Observer {
        if (!it.isSuccessful) {
            Toast.makeText(this, "City not found! Please, try again", Toast.LENGTH_SHORT).show()
        } else {
            txt_weather.text = it.body()!!.weather.first().main
            txt_temperature.text = "${it.body()!!.main.tempMax} $symbols"
            txt_feels_like.text = "${it.body()!!.main.temp} $symbols"
            setWeatherIcon(it.body()!!.weather.first().main)
        }
    })

    fun setWeatherIcon(weather: String) = when (weather) {
        "Thunderstorm" -> img_weather.background = getDrawable(R.drawable.ic_thunder)
        "Drizzle", "Rain" -> img_weather.background = getDrawable(R.drawable.ic_rainy)
        "Snow" -> img_weather.background = getDrawable(R.drawable.ic_snowy)
        "Clouds" -> img_weather.background = getDrawable(R.drawable.ic_cloudy)
        "Clear" -> img_weather.background = getDrawable(R.drawable.ic_sunny)
        else -> {
            img_weather.background = getDrawable(R.drawable.ic_sunny)
            Toast.makeText(
                this,
                "I don't know the weather and set the Sun icon!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun switcherListener() {
        units = "metric"
        symbols = getString(R.string.celsius)
        switcher_weather.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                units = "imperial"
                symbols = getString(R.string.fahrenheit)
            } else {
                units = "metric"
                symbols = getString(R.string.celsius)
            }
        }
    }

}