package com.example.hendratay.whatheweather.data.entity

import com.google.gson.annotations.SerializedName

data class CityEntity(@SerializedName("id") var cityId: Int,
                      @SerializedName("name") var cityName: String,
                      @SerializedName("coord") var coordinate: CoordinateEntity,
                      @SerializedName("country") var countryCode: String?)