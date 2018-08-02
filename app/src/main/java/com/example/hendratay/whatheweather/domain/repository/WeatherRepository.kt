package com.example.hendratay.whatheweather.domain.repository

import com.example.hendratay.whatheweather.domain.model.CurrentWeather
import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import com.example.hendratay.whatheweather.domain.model.Weather
import io.reactivex.Observable

interface WeatherRepository {

    fun getCurrentWeather(latitude: Double, longitude: Double, units: String): Observable<CurrentWeather>

    fun getWeatherForecast(latitude: Double, longitude: Double, units: String): Observable<WeatherForecast>

}