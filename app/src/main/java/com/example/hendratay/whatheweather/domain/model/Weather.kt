package com.example.hendratay.whatheweather.domain.model

data class Weather(val id: Int,
                   val main: String,
                   val description: String,
                   val icon: String)