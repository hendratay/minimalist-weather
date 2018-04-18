package com.example.hendratay.whatheweather.domain.repository

import com.example.hendratay.whatheweather.domain.model.CurrentWeather
import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import com.example.hendratay.whatheweather.domain.model.Weather
import io.reactivex.Observable

interface WeatherRepository {

    fun getCurrentWeather(cityName: String): Observable<CurrentWeather>

    fun getWeatherForecast(cityName: String): Observable<WeatherForecast>

}