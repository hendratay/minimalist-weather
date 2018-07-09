package com.example.hendratay.whatheweather.data.entity

import com.google.gson.annotations.SerializedName

data class TimeZoneEntity(@SerializedName("dstOffset") val dstOffset: Long,
                          @SerializedName("rawOffset") val rawOffset: Long,
                          @SerializedName("timeZoneId") val timeZoneId: String?,
                          @SerializedName("timeZoneName") val timeZoneName: String?,
                          @SerializedName("status") val status: String)