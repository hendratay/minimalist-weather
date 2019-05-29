package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.CoordinateEntity
import com.minimalist.weather.domain.model.Coordinate
import javax.inject.Inject

class CoordinateMapper @Inject constructor(): Mapper<CoordinateEntity, Coordinate> {

    override fun mapFromEntity(type: CoordinateEntity): Coordinate {
        return Coordinate(type.latitude, type.longitude)
    }

    override fun mapToEntity(type: Coordinate): CoordinateEntity {
        return CoordinateEntity(type.latitude, type.longitude)
    }

}