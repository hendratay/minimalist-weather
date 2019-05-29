package com.minimalist.weather.domain.model

data class TimeZone(val dstOffset: Long,
                    val rawOffset: Long,
                    val timeZoneId: String?,
                    val timeZoneName: String?,
                    val status: String)