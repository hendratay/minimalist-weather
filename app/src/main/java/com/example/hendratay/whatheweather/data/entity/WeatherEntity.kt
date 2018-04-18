package com.example.hendratay.whatheweather.data.entity

import com.google.gson.annotations.SerializedName

data class WeatherEntity(@SerializedName("id") var id: Int,
                         @SerializedName("main") var main: String,
                         @SerializedName("description") var description: String,
                         @SerializedName("icon") var icon: String)