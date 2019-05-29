package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.SnowEntity
import com.minimalist.weather.domain.model.Snow
import javax.inject.Inject

class SnowMapper @Inject constructor(): Mapper<SnowEntity?, Snow?> {

    override fun mapFromEntity(type: SnowEntity?): Snow? {
        return Snow(type?.snowVolume)
    }

    override fun mapToEntity(type: Snow?): SnowEntity? {
        return SnowEntity(type?.snowVolume)
    }

}
