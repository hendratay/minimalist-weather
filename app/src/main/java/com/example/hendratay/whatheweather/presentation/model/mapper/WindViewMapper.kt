package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Wind
import com.example.hendratay.whatheweather.presentation.model.WindView
import javax.inject.Inject

class WindViewMapper @Inject constructor(): Mapper<WindView, Wind> {

    override fun mapToView(type: Wind): WindView {
        return WindView(type.speed, type.degree)
    }

}