package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.TimeZoneEntity
import com.example.hendratay.whatheweather.domain.model.TimeZone
import javax.inject.Inject

class TimeZoneMapper @Inject constructor(): Mapper<TimeZoneEntity, TimeZone> {

    override fun mapFromEntity(type: TimeZoneEntity): TimeZone {
        return TimeZone(type.dstOffset, type.rawOffset, type.timeZoneId, type.timeZoneName, type.status)
    }

    override fun mapToEntity(type: TimeZone): TimeZoneEntity {
        return TimeZoneEntity(type.dstOffset, type.rawOffset, type.timeZoneId, type.timeZoneName, type.status)
    }

}
