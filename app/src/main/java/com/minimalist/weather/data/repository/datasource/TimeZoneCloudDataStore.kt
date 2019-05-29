package com.minimalist.weather.data.repository.datasource

import com.minimalist.weather.data.entity.TimeZoneEntity
import com.minimalist.weather.data.remote.TimeZoneServiceFactory
import io.reactivex.Single

class TimeZoneCloudDataStore: TimeZoneDataStore {

    override fun getTimeZone(location: String, timestamp: Long): Single<TimeZoneEntity> {
        return TimeZoneServiceFactory.makeService().timeZone(location, timestamp)
    }

}
