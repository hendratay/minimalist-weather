package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Cloud
import com.example.hendratay.whatheweather.presentation.model.CloudView
import javax.inject.Inject

class CloudViewMapper @Inject constructor(): Mapper<CloudView, Cloud> {

    override fun mapToView(type: Cloud): CloudView {
        return CloudView(type.cloudiness)
    }

}