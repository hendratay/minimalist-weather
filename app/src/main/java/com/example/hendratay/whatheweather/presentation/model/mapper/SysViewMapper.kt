package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Sys
import com.example.hendratay.whatheweather.presentation.model.SysView
import javax.inject.Inject

class SysViewMapper @Inject constructor(): Mapper<SysView, Sys> {

    override fun mapToView(type: Sys): SysView {
        return SysView(type.countryCode, type.sunriseTime, type.sunsetTime)
    }

}