package com.example.hendratay.whatheweather.presentation.model

import java.io.Serializable

data class ForecastView(val dateTime: Long,
                        val main: MainView,
                        val weatherList: List<WeatherView>,
                        val clouds: CloudView,
                        val wind: WindView,
                        val rain: RainView?,
                        val snow: SnowView?,
                        val dateTimeText: String) : Serializable
