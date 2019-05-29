package com.minimalist.weather.data.repository.datasource

import com.minimalist.weather.data.entity.WeatherForecastEntity
import com.minimalist.weather.data.entity.CurrentWeatherEntity
import io.reactivex.Observable

interface WeatherDataStore {

    fun getCurrentWeather(latitude: Double, longitude: Double, units: String): Observable<CurrentWeatherEntity>

    fun getWeatherForecast(latitude: Double, longitude: Double, units: String): Observable<WeatherForecastEntity>

}