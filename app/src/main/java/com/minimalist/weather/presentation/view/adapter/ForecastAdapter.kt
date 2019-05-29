package com.minimalist.weather.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minimalist.weather.R
import com.minimalist.weather.presentation.model.ForecastView
import com.minimalist.weather.presentation.view.utils.TimeFormat
import com.minimalist.weather.presentation.view.utils.WeatherIcon
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastAdapter(val forecastList: List<ForecastView>,
                      val clickListener: (ForecastView) -> Unit):
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
        holder.itemView.setOnClickListener { clickListener(forecast) }
    }

    inner class ForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var dateTimeTextview = itemView.item_date_time_text_view
        var weatherDescTextView = itemView.item_weather_desc_text_view
        var weatherIconImageView = itemView.item_weather_icon_image_view
    }

}
