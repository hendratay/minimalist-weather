package com.example.hendratay.whatheweather.data.entity

import com.google.gson.annotations.SerializedName

data class CurrentWeatherEntity(@SerializedName("coord") var coordinate: CoordinateEntity,
                                @SerializedName("weather") var weatherList: List<WeatherEntity>,
                                @SerializedName("main") var main: MainEntity,
                                @SerializedName("wind") var wind: WindEntity,
                                @SerializedName("clouds") var clouds: CloudEntity,
                                @SerializedName("rain") var rain: RainEntity?,
                                @SerializedName("snow") var snow: SnowEntity?,
                                @SerializedName("dt") var dateTime: Long,
                                @SerializedName("sys") var sys: SysEntity,
                                @SerializedName("id") var cityId: Int,
                                @SerializedName("name") var cityName: String)