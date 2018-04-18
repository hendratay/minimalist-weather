package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.SnowEntity
import com.example.hendratay.whatheweather.domain.model.Snow
import javax.inject.Inject

class SnowMapper @Inject constructor(): Mapper<SnowEntity?, Snow?> {

    override fun mapFromEntity(type: SnowEntity?): Snow? {
        return Snow(type?.snowVolume)
    }

    override fun mapToEntity(type: Snow?): SnowEntity? {
        return SnowEntity(type?.snowVolume)
    }

}
