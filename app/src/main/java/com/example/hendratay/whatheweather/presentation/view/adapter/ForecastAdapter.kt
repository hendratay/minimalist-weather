package com.example.hendratay.whatheweather.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.model.ForecastView
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon
import kotlinx.android.synthetic.main.item_forecast.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class ForecastAdapter(val forecastList: List<ForecastView>): RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun getItemCount() = forecastList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecastList.get(position))
    }

    inner class ForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(forecastView: ForecastView) {
            with(forecastView) {
                val sdf = SimpleDateFormat("E, d/M")
                itemView.date_time_text_view.text = sdf.format(Date(forecastView.dateTime * 1000)).toUpperCase()
                itemView.temperature_text_view.text = forecastView.main.temp.roundToInt().toString() + " \u2103"
                itemView.weather_condition_image_view.setImageResource(WeatherIcon.getWeatherId(forecastView.weatherList.get(0).id,
                        forecastView.weatherList.get(0).icon))
            }
        }

    }

}
