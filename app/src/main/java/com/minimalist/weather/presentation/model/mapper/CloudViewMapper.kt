package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.Cloud
import com.minimalist.weather.presentation.model.CloudView
import javax.inject.Inject

class CloudViewMapper @Inject constructor(): Mapper<CloudView, Cloud> {

    override fun mapToView(type: Cloud): CloudView {
        return CloudView(type.cloudiness)
    }

}