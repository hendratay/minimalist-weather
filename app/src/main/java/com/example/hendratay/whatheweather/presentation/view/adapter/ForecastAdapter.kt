package com.example.hendratay.whatheweather.presentation.view.adapter

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
import kotlinx.android.synthetic.main.item_forecast.view.*
import kotlin.math.roundToInt

class ForecastAdapter(val forecastList: List<ListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = forecastList.size

    override fun getItemViewType(position: Int) = forecastList.get(position).getType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewholder: RecyclerView.ViewHolder? = null
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        when(viewType) {
            ListItem.TYPE_GENERAL -> {
                val v1: View = inflater.inflate(R.layout.item_forecast, parent, false)
                viewholder = GeneralViewHolder(v1)
            }
            ListItem.TYPE_DATE -> {
                val v2: View = inflater.inflate(R.layout.item_date, parent, false)
                viewholder = DateViewHolder(v2)
            }
        }
        return viewholder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            ListItem.TYPE_GENERAL -> {
                var generalItem: GeneralItem = forecastList.get(position) as GeneralItem
                var generalViewHolder: GeneralViewHolder = holder as GeneralViewHolder
                generalViewHolder.weatherDescTextView.text = generalItem.forecastView.weatherList[0].description.toUpperCase()
                generalViewHolder.tempTextview.text = generalItem.forecastView.main.temp.roundToInt().toString() + " \u2103"
                generalViewHolder.weatherIconImageView.setImageResource(WeatherIcon.getWeatherId(generalItem.forecastView.weatherList[0].id,
                        generalItem.forecastView.weatherList[0].icon))
            }
            ListItem.TYPE_DATE -> {
                var dateItem: DateItem = forecastList.get(position) as DateItem
                var dateViewHolder: DateViewHolder = holder as DateViewHolder
                dateViewHolder.dateTextView.text = dateItem.date
            }
        }

    }

    inner class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var dateTextView = itemView.item_date_time_text_view
    }

    inner class GeneralViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tempTextview = itemView.item_temp_text_view
        var weatherDescTextView = itemView.item_weather_desc_text_view
        var weatherIconImageView = itemView.item_weather_icon_image_view
    }

}
