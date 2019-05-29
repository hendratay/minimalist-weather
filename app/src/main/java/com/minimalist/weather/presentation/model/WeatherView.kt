package com.minimalist.weather.presentation.model

data class WeatherView(val id: Int,
                       val main: String,
                       val description: String,
                       val icon: String)