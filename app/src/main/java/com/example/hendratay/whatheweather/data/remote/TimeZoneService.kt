package com.example.hendratay.whatheweather.data.remote

import com.example.hendratay.whatheweather.data.entity.TimeZoneEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeZoneService {

    @GET("json")
    fun timeZone(@Query("location") location: String, @Query("timestamp") timestamp: Long): Single<TimeZoneEntity>

}
