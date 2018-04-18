package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import com.example.hendratay.whatheweather.presentation.model.WeatherForecastView
import javax.inject.Inject

class WeatherForecastViewMapper @Inject constructor(val cityViewMapper: CityViewMapper,
                                                    val forecastViewMapper: ForecastViewMapper):
        Mapper<WeatherForecastView, WeatherForecast> {

    override fun mapToView(type: WeatherForecast): WeatherForecastView {
        return WeatherForecastView(type.numberLine,
                cityViewMapper.mapToView(type.city),
                forecastViewMapper.mapToView(type.forecastList))
    }

}