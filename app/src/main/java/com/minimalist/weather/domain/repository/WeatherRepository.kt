package com.minimalist.weather.domain.repository

import com.minimalist.weather.domain.model.CurrentWeather
import com.minimalist.weather.domain.model.WeatherForecast
import io.reactivex.Observable

interface WeatherRepository {

    fun getCurrentWeather(latitude: Double, longitude: Double, units: String): Observable<CurrentWeather>

    fun getWeatherForecast(latitude: Double, longitude: Double, units: String): Observable<WeatherForecast>

}