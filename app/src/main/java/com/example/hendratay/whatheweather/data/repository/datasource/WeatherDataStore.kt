package com.example.hendratay.whatheweather.data.repository.datasource

import com.example.hendratay.whatheweather.data.entity.WeatherForecastEntity
import com.example.hendratay.whatheweather.data.entity.CurrentWeatherEntity
import io.reactivex.Observable

interface WeatherDataStore {

    fun getCurrentWeather(cityName: String): Observable<CurrentWeatherEntity>

    fun getWeatherForecast(cityName: String): Observable<WeatherForecastEntity>

}