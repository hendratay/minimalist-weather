package com.minimalist.weather.domain.model

data class Forecast(val dateTime: Long,
                    val main: Main,
                    val weatherList: List<Weather>,
                    val clouds: Cloud,
                    val wind: Wind,
                    val rain: Rain?,
                    val snow: Snow?,
                    val dateTimeText: String)