package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.RainEntity
import com.example.hendratay.whatheweather.domain.model.Rain
import javax.inject.Inject

class RainMapper @Inject constructor(): Mapper<RainEntity?, Rain?> {

    override fun mapFromEntity(type: RainEntity?): Rain? {
        return Rain(type?.rainVolume)
    }

    override fun mapToEntity(type: Rain?): RainEntity? {
        return RainEntity(type?.rainVolume)
    }

}
