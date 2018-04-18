package com.example.hendratay.whatheweather.domain.model

data class WeatherForecast(val numberLine: Int,
                           val city: City,
                           val forecastList: List<Forecast>)