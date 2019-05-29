package com.minimalist.weather.presentation.model

data class CityView(val cityId: Int,
                    val cityName: String?,
                    val coordinate: CoordinateView,
                    val countryCode: String?)