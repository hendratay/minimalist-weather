package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.WindEntity
import com.example.hendratay.whatheweather.domain.model.Wind
import javax.inject.Inject

class WindMapper @Inject constructor(): Mapper<WindEntity, Wind> {

    override fun mapFromEntity(type: WindEntity): Wind {
        return Wind(type.speed, type.degree)
    }

    override fun mapToEntity(type: Wind): WindEntity {
        return WindEntity(type.speed, type.degree)
    }

}