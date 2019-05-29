package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.MainEntity
import com.minimalist.weather.domain.model.Main
import javax.inject.Inject

class MainMapper @Inject constructor(): Mapper<MainEntity, Main> {

    override fun mapFromEntity(type: MainEntity): Main {
        return Main(type.temp, type.tempMin, type.tempMax, type.pressure, type.seaLevel, type.groundLevel, type.humidity)
    }

    override fun mapToEntity(type: Main): MainEntity {
        return MainEntity(type.temp, type.tempMin, type.tempMax, type.pressure, type.seaLevel, type.groundLevel, type.humidity)
    }

}