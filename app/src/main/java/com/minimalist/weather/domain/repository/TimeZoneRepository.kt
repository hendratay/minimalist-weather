package com.minimalist.weather.domain.repository

import com.minimalist.weather.domain.model.TimeZone
import io.reactivex.Single

interface TimeZoneRepository {

    fun getTimeZone(location: String, timestamp: Long): Single<TimeZone>

}