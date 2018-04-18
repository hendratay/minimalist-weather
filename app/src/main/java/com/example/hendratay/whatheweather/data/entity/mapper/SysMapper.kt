package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.SysEntity
import com.example.hendratay.whatheweather.domain.model.Sys
import javax.inject.Inject

class SysMapper @Inject constructor(): Mapper<SysEntity, Sys> {

    override fun mapFromEntity(type: SysEntity): Sys {
        return Sys(type.countryCode, type.sunriseTime, type.sunsetTime)
    }

    override fun mapToEntity(type: Sys): SysEntity {
        return SysEntity(type.countryCode, type.sunriseTime, type.sunsetTime)
    }

}
