package com.minimalist.weather.data.repository.datasource

import com.minimalist.weather.data.entity.TimeZoneEntity
import io.reactivex.Single

interface TimeZoneDataStore {

    fun getTimeZone(location: String, timestamp: Long): Single<TimeZoneEntity>

}