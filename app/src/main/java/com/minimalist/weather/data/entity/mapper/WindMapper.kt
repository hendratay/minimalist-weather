package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.WindEntity
import com.minimalist.weather.domain.model.Wind
import javax.inject.Inject

class WindMapper @Inject constructor(): Mapper<WindEntity, Wind> {

    override fun mapFromEntity(type: WindEntity): Wind {
        return Wind(type.speed, type.degree)
    }

    override fun mapToEntity(type: Wind): WindEntity {
        return WindEntity(type.speed, type.degree)
    }

}