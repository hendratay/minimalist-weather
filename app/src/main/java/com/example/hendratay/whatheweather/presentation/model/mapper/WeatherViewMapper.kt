package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Weather
import com.example.hendratay.whatheweather.presentation.model.WeatherView

class WeatherViewMapper: Mapper<WeatherView, Weather> {

    override fun mapToView(type: Weather): WeatherView {
        return WeatherView(type.id, type.main, type.description, type.icon)
    }

    fun mapToView(list: List<Weather>): List<WeatherView> {
        return list.map { mapToView(it) }
    }

}