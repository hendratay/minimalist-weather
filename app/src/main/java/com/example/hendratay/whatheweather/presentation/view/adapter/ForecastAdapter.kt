package com.example.hendratay.whatheweather.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.model.ForecastView
import com.example.hendratay.whatheweather.presentation.view.utils.TimeFormat
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon
import kotlinx.android.synthetic.main.item_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(val forecastList: List<ForecastView>):
        RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun getItemCount() = forecastList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecastList.get(position)
        holder.weatherDescTextView.text = forecast.weatherList[0].description
        holder.weatherIconImageView.setImageResource(WeatherIcon.getWeatherId(forecast.weatherList[0].id, forecast.weatherList[0].icon))
        holder.dateTimeTextview.text = TimeFormat.todayForecastTime(forecast.dateTime * 1000).toLowerCase()
    }

    inner class ForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var dateTimeTextview = itemView.item_date_time_text_view
        var weatherDescTextView = itemView.item_weather_desc_text_view
        var weatherIconImageView = itemView.item_weather_icon_image_view
    }

}
