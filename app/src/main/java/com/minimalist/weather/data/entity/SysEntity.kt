package com.minimalist.weather.data.entity

import com.google.gson.annotations.SerializedName

data class SysEntity(@SerializedName("country") var countryCode: String?,
                     @SerializedName("sunrise") var sunriseTime: Long,
                     @SerializedName("sunset") var sunsetTime: Long)