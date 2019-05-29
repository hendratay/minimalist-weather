package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.Wind
import com.minimalist.weather.presentation.model.WindView
import javax.inject.Inject

class WindViewMapper @Inject constructor(): Mapper<WindView, Wind> {

    override fun mapToView(type: Wind): WindView {
        return WindView(type.speed, type.degree)
    }

}