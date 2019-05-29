package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.WeatherForecastEntity
import com.minimalist.weather.domain.model.WeatherForecast
import javax.inject.Inject

class WeatherForecastMapper @Inject constructor(val cityMapper: CityMapper, val forecastMapper: ForecastMapper):
        Mapper<WeatherForecastEntity, WeatherForecast> {

    override fun mapFromEntity(type: WeatherForecastEntity): WeatherForecast {
        return WeatherForecast(type.numberLine, cityMapper.mapFromEntity(type.city), forecastMapper.mapFromEntity(type.forecastList))
    }

    override fun mapToEntity(type: WeatherForecast): WeatherForecastEntity {
        return WeatherForecastEntity(type.numberLine, cityMapper.mapToEntity(type.city), forecastMapper.mapToEntity(type.forecastList))
    }

}