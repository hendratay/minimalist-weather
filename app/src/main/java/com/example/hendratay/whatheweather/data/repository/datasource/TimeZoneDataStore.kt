package com.example.hendratay.whatheweather.data.repository.datasource

import com.example.hendratay.whatheweather.data.entity.TimeZoneEntity
import io.reactivex.Single

interface TimeZoneDataStore {

    fun getTimeZone(location: String, timestamp: Long): Single<TimeZoneEntity>

}