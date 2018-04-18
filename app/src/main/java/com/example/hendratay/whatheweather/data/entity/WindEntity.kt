package com.example.hendratay.whatheweather.data.entity

import com.google.gson.annotations.SerializedName

data class WindEntity(@SerializedName("speed") var speed: Double,
                      @SerializedName("deg") var degree: Double)