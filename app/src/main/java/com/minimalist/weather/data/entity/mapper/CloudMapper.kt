package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.CloudEntity
import com.minimalist.weather.domain.model.Cloud
import javax.inject.Inject

class CloudMapper @Inject constructor(): Mapper<CloudEntity, Cloud> {

    override fun mapFromEntity(type: CloudEntity): Cloud {
        return Cloud(type.cloudiness)
    }

    override fun mapToEntity(type: Cloud): CloudEntity {
        return CloudEntity(type.cloudiness)
    }

}