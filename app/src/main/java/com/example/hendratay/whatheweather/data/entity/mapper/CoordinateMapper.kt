package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.CoordinateEntity
import com.example.hendratay.whatheweather.domain.model.Coordinate
import javax.inject.Inject

class CoordinateMapper @Inject constructor(): Mapper<CoordinateEntity, Coordinate> {

    override fun mapFromEntity(type: CoordinateEntity): Coordinate {
        return Coordinate(type.latitude, type.longitude)
    }

    override fun mapToEntity(type: Coordinate): CoordinateEntity {
        return CoordinateEntity(type.latitude, type.longitude)
    }

}