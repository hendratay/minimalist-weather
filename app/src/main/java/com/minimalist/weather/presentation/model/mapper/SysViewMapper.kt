package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.Sys
import com.minimalist.weather.presentation.model.SysView
import javax.inject.Inject

class SysViewMapper @Inject constructor(): Mapper<SysView, Sys> {

    override fun mapToView(type: Sys): SysView {
        return SysView(type.countryCode, type.sunriseTime, type.sunsetTime)
    }

}