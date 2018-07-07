package com.example.hendratay.whatheweather.data.repository

import com.example.hendratay.whatheweather.data.entity.mapper.TimeZoneMapper
import com.example.hendratay.whatheweather.data.repository.datasource.TimeZoneDataStoreFactory
import com.example.hendratay.whatheweather.domain.model.TimeZone
import com.example.hendratay.whatheweather.domain.repository.TimeZoneRepository
import io.reactivex.Single
import javax.inject.Inject

class TimeZoneDataRepository @Inject constructor(val factory: TimeZoneDataStoreFactory,
                                                 private val timeZoneMapper: TimeZoneMapper):
        TimeZoneRepository {

    override fun getTimeZone(location: String, timestamp: Long): Single<TimeZone> {
        return factory.create().getTimeZone(location, timestamp)
                .map { timeZoneMapper.mapFromEntity(it) }
    }

}
