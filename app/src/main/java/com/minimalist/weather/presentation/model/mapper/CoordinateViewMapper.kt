package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.Coordinate
import com.minimalist.weather.presentation.model.CoordinateView
import javax.inject.Inject

class CoordinateViewMapper @Inject constructor(): Mapper<CoordinateView, Coordinate> {

    override fun mapToView(type: Coordinate): CoordinateView {
        return CoordinateView(type.latitude, type.longitude)
    }

}