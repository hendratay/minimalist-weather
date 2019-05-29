package com.minimalist.weather.presentation.view.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.minimalist.weather.R
import com.minimalist.weather.presentation.model.ForecastView
import kotlinx.android.synthetic.main.item_forecast_week.view.*

class ForecastWeeklyAdapter(val forecastList: List<Map<String, List<ForecastView>>>,
                            val clickListener: (ForecastView) -> Unit):
        RecyclerView.Adapter<ForecastWeeklyAdapter.ForecastWeeklyViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemCount() = forecastList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastWeeklyViewHolder {
        return ForecastWeeklyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast_week, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastWeeklyViewHolder, position: Int) {
        holder.weeklyForecast.recycledViewPool = viewPool
        holder.weeklyForecast.layoutManager = LinearLayoutManager(holder.weeklyForecast.context)
        for (item in forecastList[position]) {
            holder.dateTextView.text = item.key
            holder.weeklyForecast.adapter = ForecastHourAdapter(item.value) { it -> clickListener(it) }
        }
    }

    inner class ForecastWeeklyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var dateTextView: TextView = itemView.item_date_text_view
        var weeklyForecast: RecyclerView = itemView.rv_weekly_forecast
    }

}
