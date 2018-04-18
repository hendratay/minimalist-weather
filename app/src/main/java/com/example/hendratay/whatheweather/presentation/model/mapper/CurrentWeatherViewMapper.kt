package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.CurrentWeather
import com.example.hendratay.whatheweather.presentation.model.CurrentWeatherView

class CurrentWeatherViewMapper(val coordinateViewMapper: CoordinateViewMapper,
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