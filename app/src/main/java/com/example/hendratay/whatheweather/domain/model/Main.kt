package com.example.hendratay.whatheweather.domain.model

data class Main(val temp: Double,
                val tempMin: Double,
                val tempMax: Double,
                val pressure: Double,
                val seaLevel: Double,
                val groundLevel: Double,
                val humidity: Int)