package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.WeatherForecastEntity
import com.example.hendratay.whatheweather.domain.model.WeatherForecast

class WeatherForecastMapper(val cityMapper: CityMapper, val forecastMapper: ForecastMapper):
        Mapper<WeatherForecastEntity, WeatherForecast> {

    override fun mapFromEntity(type: WeatherForecastEntity): WeatherForecast {
        return WeatherForecast(type.numberLine, cityMapper.mapFromEntity(type.city), forecastMapper.mapFromEntity(type.forecastList))
    }

    override fun mapToEntity(type: WeatherForecast): WeatherForecastEntity {
        return WeatherForecastEntity(type.numberLine, cityMapper.mapToEntity(type.city), forecastMapper.mapToEntity(type.forecastList))
    }

}