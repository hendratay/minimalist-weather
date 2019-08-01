package com.minimalist.weather.presentation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.minimalist.weather.R
import com.minimalist.weather.presentation.model.ForecastView
import com.minimalist.weather.presentation.view.utils.WeatherIcon
import kotlinx.android.synthetic.main.item_forecast_detail.*
import kotlin.math.roundToInt

class WeatherDetailFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_forecast_detail, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val forecastView = arguments?.getSerializable("FORECAST_VIEW") as ForecastView
        weather_icon_image_view.setImageResource(WeatherIcon.getWeatherId(forecastView.weatherList[0].id, forecastView.weatherList[0].icon))
        weather_desc_text_view.text = forecastView.weatherList[0].description.toUpperCase()
        temp_text_view.text = "${forecastView.main.temp.roundToInt()}\u00b0"
        min_temp_text_view.text = "\u25bc ${forecastView.main.tempMin.roundToInt()}\u00b0"
        max_temp_text_view.text = "\u25b2 ${forecastView.main.tempMax.roundToInt()}\u00b0"
        wind_text_view.text = "${forecastView.wind.speed} m/s"
        pressure_text_view.text = "${forecastView.main.pressure.roundToInt()} hPa"
        humidity_text_view.text = "${forecastView.main.humidity} %"
        cloud_text_view.text = "${forecastView.clouds.cloudiness} %"
    }

}
