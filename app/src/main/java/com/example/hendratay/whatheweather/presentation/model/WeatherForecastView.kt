package com.example.hendratay.whatheweather.presentation.model

data class WeatherForecastView(val numberLine: Int,
                               val city: CityView,
                               val forecastList: List<ForecastView>)