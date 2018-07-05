package com.example.hendratay.whatheweather.domain.repository

import com.example.hendratay.whatheweather.domain.model.TimeZone
import io.reactivex.Single

interface TimeZoneRepository {

    fun getTimeZone(location: String, timestamp: Long): Single<TimeZone>

}