package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Forecast
import com.example.hendratay.whatheweather.presentation.model.ForecastView
import javax.inject.Inject

class ForecastViewMapper @Inject constructor(val mainViewMapper: MainViewMapper,
                                             val weatherViewMapper: WeatherViewMapper,
                                             val cloudViewMapper: CloudViewMapper,
                                             val windViewMapper: WindViewMapper,
                                             val rainViewMapper: RainViewMapper,
                                             val snowViewMapper: SnowViewMapper):
        Mapper<ForecastView, Forecast> {

    override fun mapToView(type: Forecast): ForecastView {
        return ForecastView(type.dateTime,
                mainViewMapper.mapToView(type.main),
                weatherViewMapper.mapToView(type.weatherList),
                cloudViewMapper.mapToView(type.clouds),
                windViewMapper.mapToView(type.wind),
                rainViewMapper.mapToView(type.rain),
                snowViewMapper.mapToView(type.snow),
                type.dateTimeText)
    }

    fun mapToView(list: List<Forecast>): List<ForecastView> {
        return list.map { mapToView(it) }
    }
}