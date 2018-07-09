package com.example.hendratay.whatheweather.presentation.model

data class TimeZoneView(val dstOffset: Long,
                        val rawOffset: Long,
                        val timeZoneId: String?,
                        val timeZoneName: String?,
                        val status: String)