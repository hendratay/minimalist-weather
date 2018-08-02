package com.example.hendratay.whatheweather.data.repository.datasource

import com.example.hendratay.whatheweather.data.entity.WeatherForecastEntity
import com.example.hendratay.whatheweather.data.entity.CurrentWeatherEntity
import io.reactivex.Observable

interface WeatherDataStore {

    fun getCurrentWeather(latitude: Double, longitude: Double, units: String): Observable<CurrentWeatherEntity>

    fun getWeatherForecast(latitude: Double, longitude: Double, units: String): Observable<WeatherForecastEntity>

}