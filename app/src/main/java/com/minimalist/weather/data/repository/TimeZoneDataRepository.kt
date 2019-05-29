package com.minimalist.weather.data.repository

import com.minimalist.weather.data.entity.mapper.TimeZoneMapper
import com.minimalist.weather.data.repository.datasource.TimeZoneDataStoreFactory
import com.minimalist.weather.domain.model.TimeZone
import com.minimalist.weather.domain.repository.TimeZoneRepository
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
