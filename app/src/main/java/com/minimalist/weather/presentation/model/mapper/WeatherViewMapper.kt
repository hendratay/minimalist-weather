package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.Weather
import com.minimalist.weather.presentation.model.WeatherView
import javax.inject.Inject

class WeatherViewMapper @Inject constructor(): Mapper<WeatherView, Weather> {

    override fun mapToView(type: Weather): WeatherView {
        return WeatherView(type.id, type.main, type.description, type.icon)
    }

    fun mapToView(list: List<Weather>): List<WeatherView> {
        return list.map { mapToView(it) }
    }

}