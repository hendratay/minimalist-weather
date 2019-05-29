package com.minimalist.weather.data.entity

import com.google.gson.annotations.SerializedName

data class CloudEntity(@SerializedName("all") var cloudiness: Int)