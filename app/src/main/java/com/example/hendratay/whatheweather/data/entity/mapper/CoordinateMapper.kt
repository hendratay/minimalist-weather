package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.CoordinateEntity
import com.example.hendratay.whatheweather.domain.model.Coordinate

class CoordinateMapper: Mapper<CoordinateEntity, Coordinate> {

    override fun mapFromEntity(type: CoordinateEntity): Coordinate {
        return Coordinate(type.latitude, type.longitude)
    }

    override fun mapToEntity(type: Coordinate): CoordinateEntity {
        return CoordinateEntity(type.latitude, type.longitude)
    }

}