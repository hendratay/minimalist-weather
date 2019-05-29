package com.minimalist.weather.presentation.model

data class CurrentWeatherView(val coordinate: CoordinateView,
                              val weatherList: List<WeatherView>,
                              val main: MainView,
                              val wind: WindView,
                              val clouds: CloudView,
                              val rain: RainView?,
                              val snow: SnowView?,
                              val dateTime: Long,
                              val sys: SysView,
                              val cityId: Int,
                              val cityName: String)