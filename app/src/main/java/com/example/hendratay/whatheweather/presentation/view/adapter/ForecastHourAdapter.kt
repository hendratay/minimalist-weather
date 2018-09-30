package com.example.hendratay.whatheweather.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.model.ForecastView
import com.example.hendratay.whatheweather.presentation.view.utils.TimeFormat
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon
import kotlinx.android.synthetic.main.item_forecast_hour.view.*
import kotlin.math.roundToInt

class ForecastHourAdapter(val forecastList: List<ForecastView>):
        RecyclerView.Adapter<ForecastHourAdapter.ForecastHourViewHolder>() {

    override fun getItemCount() = forecastList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHourViewHolder {
        return ForecastHourViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast_hour, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastHourViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.weatherIconImageView.setImageResource(WeatherIcon.getWeatherId(forecast.weatherList[0].id, forecast.weatherList[0].icon))
        holder.weatherDescTextView.text = forecast.weatherList[0].description
        holder.timeTextView.text = TimeFormat.forecastTime(forecast.dateTime * 1000).toLowerCase()
        holder.tempTextView.text = "${forecast.main.temp.roundToInt()}\u00b0"
    }

    inner class ForecastHourViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var weatherDescTextView: TextView = itemView.weekly_weather_desc_text_view
        var weatherIconImageView: ImageView = itemView.weekly_weather_icon_image_view
        var tempTextView: TextView = itemView.weekly_temp_text_view
        var timeTextView: TextView = itemView.weekly_time_text_view
    }

}
