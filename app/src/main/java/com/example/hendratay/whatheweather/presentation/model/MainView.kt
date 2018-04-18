package com.example.hendratay.whatheweather.presentation.model

data class MainView(val temp: Double,
                    val tempMin: Double,
                    val tempMax: Double,
                    val pressure: Double,
                    val seaLevel: Double,
                    val groundLevel: Double,
                    val humidity: Int)