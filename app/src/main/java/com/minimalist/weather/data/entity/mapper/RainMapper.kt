package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.RainEntity
import com.minimalist.weather.domain.model.Rain
import javax.inject.Inject

class RainMapper @Inject constructor(): Mapper<RainEntity?, Rain?> {

    override fun mapFromEntity(type: RainEntity?): Rain? {
        return Rain(type?.rainVolume)
    }

    override fun mapToEntity(type: Rain?): RainEntity? {
        return RainEntity(type?.rainVolume)
    }

}
