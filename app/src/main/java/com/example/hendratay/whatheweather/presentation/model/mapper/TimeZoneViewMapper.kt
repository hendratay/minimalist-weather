package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.TimeZone
import com.example.hendratay.whatheweather.presentation.model.TimeZoneView
import javax.inject.Inject

class TimeZoneViewMapper @Inject constructor() : Mapper<TimeZoneView, TimeZone> {

    override fun mapToView(type: TimeZone): TimeZoneView {
        return TimeZoneView(type.dstOffset, type.rawOffset, type.timeZoneId, type.timeZoneName, type.status)
    }

}
