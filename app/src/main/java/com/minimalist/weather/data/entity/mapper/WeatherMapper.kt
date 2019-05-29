package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.WeatherEntity
import com.minimalist.weather.domain.model.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor(): Mapper<WeatherEntity, Weather> {

    override fun mapFromEntity(type: WeatherEntity): Weather {
        return Weather(type.id, type.main, type.description, type.icon)
    }

    override fun mapToEntity(type: Weather): WeatherEntity {
        return WeatherEntity(type.id, type.main, type.description, type.icon)
    }

    fun mapFromEntity(list: List<WeatherEntity>): List<Weather> {
        return list.map { mapFromEntity(it) }
    }

    fun mapToEntity(list: List<Weather>): List<WeatherEntity> {
        return list.map { mapToEntity(it) }
    }

}