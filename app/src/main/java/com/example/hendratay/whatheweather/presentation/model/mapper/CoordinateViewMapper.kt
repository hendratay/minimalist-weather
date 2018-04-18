package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Coordinate
import com.example.hendratay.whatheweather.presentation.model.CoordinateView

class CoordinateViewMapper: Mapper<CoordinateView, Coordinate> {

    override fun mapToView(type: Coordinate): CoordinateView {
        return CoordinateView(type.latitude, type.longitude)
    }

}