package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.MainEntity
import com.example.hendratay.whatheweather.domain.model.Main

class MainMapper: Mapper<MainEntity, Main> {

    override fun mapFromEntity(type: MainEntity): Main {
        return Main(type.temp, type.tempMin, type.tempMax, type.pressure, type.seaLevel, type.groundLevel, type.humidity)
    }

    override fun mapToEntity(type: Main): MainEntity {
        return MainEntity(type.temp, type.tempMin, type.tempMax, type.pressure, type.seaLevel, type.groundLevel, type.humidity)
    }

}