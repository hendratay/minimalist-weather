package com.minimalist.weather.domain.model

data class City(val cityId: Int,
                val cityName: String?,
                val coordinate: Coordinate,
                val countryCode: String?)
