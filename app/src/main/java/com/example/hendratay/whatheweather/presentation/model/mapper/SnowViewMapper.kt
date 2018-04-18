package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Snow
import com.example.hendratay.whatheweather.presentation.model.SnowView
import javax.inject.Inject

class SnowViewMapper @Inject constructor(): Mapper<SnowView?, Snow?> {

    override fun mapToView(type: Snow?): SnowView? {
        return SnowView(type?.snowVolume)
    }

}
