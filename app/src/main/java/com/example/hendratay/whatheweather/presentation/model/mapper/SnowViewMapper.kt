package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Snow
import com.example.hendratay.whatheweather.presentation.model.SnowView

class SnowViewMapper: Mapper<SnowView?, Snow?> {

    override fun mapToView(type: Snow?): SnowView? {
        return SnowView(type?.snowVolume)
    }

}
