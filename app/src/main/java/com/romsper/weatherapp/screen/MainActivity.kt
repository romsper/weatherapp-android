package com.romsper.weatherapp.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import com.roacult.backdrop.BackdropLayout
import com.romsper.weatherapp.R
import com.romsper.weatherapp.fragment.WeatherViewModel
import com.romsper.weatherapp.model.WeatherModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.frontlayer.*
import kotlinx.android.synthetic.main.wether_fragment.*

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var weatherViewModel: WeatherViewModel
    private var units: String = ""
    private var symbols: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        backdropListener()
        switcherListener()
        weatherObserver()
        searchListener()
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
                Log.v("Search", "onItemSelected: index: $index, query: $s")
            }

            override fun onTextChanged(index: Int, s: CharSequence) {
                Log.v("Search", "changed: index: $index, query: $s")
            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                weatherViewModel.getWeather(s.toString(), units)
                Log.v("Search", "complete: index: $index, query: $s")
            }

            override fun onSearchItemRemoved(index: Int) {
                Log.v("Search", "remove: index: $index")
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
            setWeatherIcon(it.body()!!)
        }
    })

    private fun setWeatherIcon(obj: WeatherModel) = when (obj.weather.first().main) {
        "Thunderstorm" -> img_weather.background = getDrawable(R.drawable.ic_thunder)
        "Drizzle" -> img_weather.background = getDrawable(R.drawable.ic_rainy)
        "Rain" -> img_weather.background = getDrawable(R.drawable.ic_rainy)
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