package com.minimalist.weather.presentation.model

data class WeatherForecastView(val numberLine: Int,
                               val city: CityView,
                               val forecastList: List<ForecastView>)