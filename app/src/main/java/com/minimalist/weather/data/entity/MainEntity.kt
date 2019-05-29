package com.minimalist.weather.data.entity

import com.google.gson.annotations.SerializedName

data class MainEntity(@SerializedName("temp") var temp: Double,
                      @SerializedName("temp_min") var tempMin: Double,
                      @SerializedName("temp_max") var tempMax: Double,
                      @SerializedName("pressure") var pressure: Double,
                      @SerializedName("sea_level") var seaLevel: Double,
                      @SerializedName("grnd_level") var groundLevel: Double,
                      @SerializedName("humidity") var humidity: Int)