package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.ForecastEntity
import com.minimalist.weather.domain.model.Forecast
import javax.inject.Inject

class ForecastMapper @Inject constructor(val mainMapper: MainMapper,
                                         val weatherMapper: WeatherMapper,
                                         val cloudMapper: CloudMapper,
                                         val windMapper: WindMapper,
                                         val rainMapper: RainMapper,
                                         val snowMapper: SnowMapper):
        Mapper<ForecastEntity, Forecast> {

    override fun mapFromEntity(type: ForecastEntity): Forecast {
        return Forecast(type.dateTime,
                mainMapper.mapFromEntity(type.main),
                weatherMapper.mapFromEntity(type.weatherList),
                cloudMapper.mapFromEntity(type.clouds),
                windMapper.mapFromEntity(type.wind),
                rainMapper.mapFromEntity(type.rain),
                snowMapper.mapFromEntity(type.snow),
                type.dateTimeText)
    }

    override fun mapToEntity(type: Forecast): ForecastEntity {
        return ForecastEntity(type.dateTime,
                mainMapper.mapToEntity(type.main),
                weatherMapper.mapToEntity(type.weatherList),
                cloudMapper.mapToEntity(type.clouds),
                windMapper.mapToEntity(type.wind),
                rainMapper.mapToEntity(type.rain),
                snowMapper.mapToEntity(type.snow),
                type.dateTimeText)
    }

    fun mapFromEntity(list: List<ForecastEntity>): List<Forecast> {
        return list.map { mapFromEntity(it) }
    }

    fun mapToEntity(list: List<Forecast>): List<ForecastEntity> {
        return list.map { mapToEntity(it) }
    }

}
