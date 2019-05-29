package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.Snow
import com.minimalist.weather.presentation.model.SnowView
import javax.inject.Inject

class SnowViewMapper @Inject constructor(): Mapper<SnowView?, Snow?> {

    override fun mapToView(type: Snow?): SnowView? {
        return SnowView(type?.snowVolume)
    }

}
