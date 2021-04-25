package com.romsper.weatherapp.ui.view

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
import com.romsper.weatherapp.ui.adapter.ForecastAdapter
import com.romsper.weatherapp.ui.viewModel.ForecastViewModel
import com.romsper.weatherapp.ui.viewModel.WeatherViewModel
import com.romsper.weatherapp.ui.viewModel.MainViewModel
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
    private var city: String = ""

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
        units = getString(R.string.metric)
        symbols = getString(R.string.celsius)
    }

    //---Search---

    private fun searchListener() =
        multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener {

            override fun onItemSelected(index: Int, s: CharSequence) {
                city = s.toString()
                initSearch()
                Log.v("SEARCH", "onItemSelected: index: $index, query: $s")
            }

            override fun onTextChanged(index: Int, s: CharSequence) {
                Log.v("SEARCH", "changed: index: $index, query: $s")
            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                city = s.toString()
                initSearch()
                Log.v("SEARCH", "complete: index: $index, query: $s")
            }

            override fun onSearchItemRemoved(index: Int) {
                Log.v("SEARCH", "remove: index: $index")
            }
        })

    private fun initSearch() {
        weatherViewModel.getWeather(city, units)
        forecastViewModel.getForecast(city, units)
    }

    //------------

    //---Observers---

    fun forecastObserver() = forecastViewModel.forecastModel.observe(this, Observer {
        if (!it.isSuccessful) {
            Toast.makeText(this, getString(R.string.city_not_found), Toast.LENGTH_SHORT).show()
        } else {
            forecast_recycler.layoutManager = LinearLayoutManager(this)
            forecast_recycler.adapter = ForecastAdapter(it.body()!!.list, symbols)
            forecast_empty_logo.visibility = View.GONE
        }
    })

    @SuppressLint("SetTextI18n")
    private fun weatherObserver() = weatherViewModel.weatherModel.observe(this, Observer {
        if (!it.isSuccessful) {
            Toast.makeText(this, getString(R.string.city_not_found), Toast.LENGTH_SHORT).show()
        } else {
            txt_weather.text = it.body()!!.weather.first().main
            txt_temperature.text = "${it.body()!!.main.tempMax} $symbols"
            txt_feels_like.text = "${it.body()!!.main.temp} $symbols"
            setWeatherIcon()
        }
    })

    //------------

    private fun setWeatherIcon() = weatherViewModel.getWeatherIcon()?.let { img_weather.background = getDrawable(it) } ?: Toast.makeText(
        this, getString(R.string.weather_unknown), Toast.LENGTH_SHORT).show()

    private fun switcherListener() {
        switcher_weather.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                units = getString(R.string.imperial)
                symbols = getString(R.string.fahrenheit)
                initSearch()
            } else {
                units = getString(R.string.metric)
                symbols = getString(R.string.celsius)
                initSearch()
            }
        }
    }

    private fun showDialog() {
        dialog.setContentView(R.layout.dialog_auth_notification)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.btn_close_dialog.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, getString(R.string.dialog_closed), Toast.LENGTH_SHORT).show() }
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
}