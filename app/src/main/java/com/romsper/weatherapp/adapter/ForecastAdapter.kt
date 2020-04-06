package com.romsper.weatherapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.romsper.weatherapp.R
import com.romsper.weatherapp.model.Forecast
import kotlinx.android.synthetic.main.forecast_fragment.view.*

class ForecastAdapter(private val items: List<Forecast>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastAdapter.ForecastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_fragment, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(items[position * 7])
    }

    override fun getItemCount() = items.size % 7

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnFocusChangeListener {
        init {
            itemView.onFocusChangeListener = this
        }

        fun bind(item: Forecast) {
            itemView.forecast_weather.text = item.weather.first().main
            itemView.forecast_temp.text = item.main.temp.toString()
            setWeatherIcon(item.weather.first().main)
        }

        private fun setWeatherIcon(weather: String) = when (weather) {
            "Thunderstorm" -> itemView.forecast_weather_icon.setBackgroundResource(R.drawable.ic_thunder)
            "Drizzle", "Rain" -> itemView.forecast_weather_icon.setBackgroundResource(R.drawable.ic_rainy)
            "Snow" -> itemView.forecast_weather_icon.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_snowy))
            "Clouds" -> itemView.forecast_weather_icon.setBackgroundResource(R.drawable.ic_cloudy)
            "Clear" -> itemView.forecast_weather_icon.setBackgroundResource(R.drawable.ic_sunny)
            else -> {
                itemView.forecast_weather_icon.setBackgroundResource(R.drawable.ic_sunny)
            }
        }

        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (hasFocus) v?.setBackgroundColor(Color.CYAN) else v?.setBackgroundColor(Color.CYAN)
        }
    }
}