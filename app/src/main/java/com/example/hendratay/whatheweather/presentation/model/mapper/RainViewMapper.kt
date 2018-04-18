package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Rain
import com.example.hendratay.whatheweather.presentation.model.RainView

class RainViewMapper: Mapper<RainView?, Rain?> {

    override fun mapToView(type: Rain?): RainView? {
        return RainView(type?.rainVolume)
    }

}
