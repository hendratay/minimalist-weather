package com.example.hendratay.whatheweather.data.entity

import com.google.gson.annotations.SerializedName

data class ForecastEntity(@SerializedName("dt") var dateTime: Long,
                          @SerializedName("main") var main: MainEntity,
                          @SerializedName("weather") var weatherList: List<WeatherEntity>,
                          @SerializedName("clouds") var clouds: CloudEntity,
                          @SerializedName("wind") var wind: WindEntity,
                          @SerializedName("rain") var rain: RainEntity?,
                          @SerializedName("snow") var snow: SnowEntity?,
                          @SerializedName("dt_txt") var dateTimeText: String)
