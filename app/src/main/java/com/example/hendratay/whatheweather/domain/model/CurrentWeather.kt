package com.example.hendratay.whatheweather.domain.model

data class CurrentWeather(val coordinate: Coordinate,
                          val weatherList: List<Weather>,
                          val main: Main,
                          val wind: Wind,
                          val clouds: Cloud,
                          val rain: Rain?,
                          val snow: Snow?,
                          val dateTime: Long,
                          val sys: Sys,
                          val cityId: Int,
                          val cityName: String)