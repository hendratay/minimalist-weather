package com.example.hendratay.whatheweather.data.entity

import com.google.gson.annotations.SerializedName

data class CoordinateEntity(@SerializedName("lat") var latitude: Double,
                            @SerializedName("lon") var longitude: Double)