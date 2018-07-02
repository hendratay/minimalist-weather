package com.example.hendratay.whatheweather.presentation.view.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.model.DateItem
import com.example.hendratay.whatheweather.presentation.model.GeneralItem
import com.example.hendratay.whatheweather.presentation.model.ListItem
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon
import kotlinx.android.synthetic.main.item_date.view.*
import kotlinx.android.synthetic.main.item_forecast_weekly.view.*


class ForecastWeeklyAdapter(val forecastList: List<ListItem>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = forecastList.size

    override fun getItemViewType(position: Int) = forecastList.get(position).getType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        when(viewType) {
            ListItem.TYPE_DATE -> {
                val v1: View = inflater.inflate(R.layout.item_date, parent, false)
                viewHolder = DateViewHolder(v1)
            }
            ListItem.TYPE_GENERAL -> {
                val v2: View = inflater.inflate(R.layout.item_forecast_weekly, parent, false)
                viewHolder = GeneralViewHolder(v2)
            }
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            ListItem.TYPE_DATE -> {
                val dateItem: DateItem = forecastList[position] as DateItem
                val dateViewHolder: DateViewHolder = holder as DateViewHolder
                dateViewHolder.dateTextview.text = dateItem.date
            }
            ListItem.TYPE_GENERAL -> {
                val generalItem: GeneralItem = forecastList[position] as GeneralItem
                val generalViewHolder: GeneralViewHolder = holder as GeneralViewHolder
                generalViewHolder.weatherDescTextView.text = generalItem.forecastView.weatherList[0].description
                generalViewHolder.weatherIconImageView.setImageResource(WeatherIcon.getWeatherId(generalItem.forecastView.weatherList[0].id,generalItem.forecastView.weatherList[0].icon))
                generalViewHolder.tempTextView.text = "${generalItem.forecastView.main.temp}\u00b0"
            }
        }

    }

    inner class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var dateTextview = itemView.item_date_text_view
    }

    inner class GeneralViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var weatherDescTextView = itemView.weekly_weather_desc_text_view
        var weatherIconImageView = itemView.weekly_weather_icon_image_view
        var tempTextView = itemView.weekly_temp_text_view
    }

}