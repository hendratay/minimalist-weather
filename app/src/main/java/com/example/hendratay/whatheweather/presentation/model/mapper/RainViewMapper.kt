package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Rain
import com.example.hendratay.whatheweather.presentation.model.RainView
import javax.inject.Inject

class RainViewMapper @Inject constructor(): Mapper<RainView?, Rain?> {

    override fun mapToView(type: Rain?): RainView? {
        return RainView(type?.rainVolume)
    }

}
