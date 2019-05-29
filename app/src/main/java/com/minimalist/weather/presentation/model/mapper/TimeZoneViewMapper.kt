package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.TimeZone
import com.minimalist.weather.presentation.model.TimeZoneView
import javax.inject.Inject

class TimeZoneViewMapper @Inject constructor() : Mapper<TimeZoneView, TimeZone> {

    override fun mapToView(type: TimeZone): TimeZoneView {
        return TimeZoneView(type.dstOffset, type.rawOffset, type.timeZoneId, type.timeZoneName, type.status)
    }

}
