package com.minimalist.weather.data.entity

import com.google.gson.annotations.SerializedName

data class WeatherForecastEntity(@SerializedName("cnt") var numberLine: Int,
                                 @SerializedName("city") var city: CityEntity,
                                 @SerializedName("list") var forecastList: List<ForecastEntity>)