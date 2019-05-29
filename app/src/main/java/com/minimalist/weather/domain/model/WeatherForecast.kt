package com.minimalist.weather.domain.model

data class WeatherForecast(val numberLine: Int,
                           val city: City,
                           val forecastList: List<Forecast>)