package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.CurrentWeather
import com.minimalist.weather.presentation.model.CurrentWeatherView
import javax.inject.Inject

class CurrentWeatherViewMapper @Inject constructor(val coordinateViewMapper: CoordinateViewMapper,
                                                   val weatherViewMapper: WeatherViewMapper,
                                                   val mainViewMapper: MainViewMapper,
                                                   val windViewMapper: WindViewMapper,
                                                   val cloudViewMapper: CloudViewMapper,
                                                   val rainViewMapper: RainViewMapper,
                                                   val snowViewMapper: SnowViewMapper,
                                                   val sysViewMapper: SysViewMapper):
        Mapper<CurrentWeatherView, CurrentWeather> {

    override fun mapToView(type: CurrentWeather): CurrentWeatherView {
        return CurrentWeatherView(coordinateViewMapper.mapToView(type.coordinate),
                weatherViewMapper.mapToView(type.weatherList),
                mainViewMapper.mapToView(type.main),
                windViewMapper.mapToView(type.wind),
                cloudViewMapper.mapToView(type.clouds),
                rainViewMapper.mapToView(type.rain),
                snowViewMapper.mapToView(type.snow),
                type.dateTime,
                sysViewMapper.mapToView(type.sys),
                type.cityId,
                type.cityName)
    }

}