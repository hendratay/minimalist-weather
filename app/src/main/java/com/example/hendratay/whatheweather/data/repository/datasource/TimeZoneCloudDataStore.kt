package com.example.hendratay.whatheweather.data.repository.datasource

import com.example.hendratay.whatheweather.data.entity.TimeZoneEntity
import com.example.hendratay.whatheweather.data.remote.TimeZoneServiceFactory
import io.reactivex.Single

class TimeZoneCloudDataStore: TimeZoneDataStore {

    override fun getTimeZone(location: String, timestamp: Long): Single<TimeZoneEntity> {
        return TimeZoneServiceFactory.makeService().timeZone(location, timestamp)
    }

}
