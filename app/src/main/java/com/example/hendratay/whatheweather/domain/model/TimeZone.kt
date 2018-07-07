package com.example.hendratay.whatheweather.domain.model

data class TimeZone(val dstOffset: Long,
                    val rawOffset: Long,
                    val timeZoneId: String,
                    val timeZoneName: String,
                    val status: String)