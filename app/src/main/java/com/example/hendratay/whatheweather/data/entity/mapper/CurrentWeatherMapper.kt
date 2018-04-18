package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.CurrentWeatherEntity
import com.example.hendratay.whatheweather.domain.model.CurrentWeather
import javax.inject.Inject

class CurrentWeatherMapper @Inject constructor(val coordinateMapper: CoordinateMapper,
                                               val weatherMapper: WeatherMapper,
                                               val mainMapper: MainMapper,
                                               val windMapper: WindMapper,
                                               val cloudMapper: CloudMapper,
                                               val rainMapper: RainMapper,
                                               val snowMapper: SnowMapper,
                                               val sysMapper: SysMapper):
        Mapper<CurrentWeatherEntity, CurrentWeather> {

    override fun mapFromEntity(type: CurrentWeatherEntity): CurrentWeather {
        return CurrentWeather(coordinateMapper.mapFromEntity(type.coordinate),
                weatherMapper.mapFromEntity(type.weatherList),
                mainMapper.mapFromEntity(type.main),
                windMapper.mapFromEntity(type.wind),
                cloudMapper.mapFromEntity(type.clouds),
                rainMapper.mapFromEntity(type.rain),
                snowMapper.mapFromEntity(type.snow),
                type.dateTime,
                sysMapper.mapFromEntity(type.sys),
                type.cityId,
                type.cityName)
    }

    override fun mapToEntity(type: CurrentWeather): CurrentWeatherEntity {
        return CurrentWeatherEntity(coordinateMapper.mapToEntity(type.coordinate),
                weatherMapper.mapToEntity(type.weatherList),
                mainMapper.mapToEntity(type.main),
                windMapper.mapToEntity(type.wind),
                cloudMapper.mapToEntity(type.clouds),
                rainMapper.mapToEntity(type.rain),
                snowMapper.mapToEntity(type.snow),
                type.dateTime,
                sysMapper.mapToEntity(type.sys),
                type.cityId,
                type.cityName)
    }

}
