package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.Rain
import com.minimalist.weather.presentation.model.RainView
import javax.inject.Inject

class RainViewMapper @Inject constructor(): Mapper<RainView?, Rain?> {

    override fun mapToView(type: Rain?): RainView? {
        return RainView(type?.rainVolume)
    }

}
