package com.example.hendratay.whatheweather.data.repository.datasource

import com.example.hendratay.whatheweather.data.entity.WeatherForecastEntity
import com.example.hendratay.whatheweather.data.entity.CurrentWeatherEntity
import com.example.hendratay.whatheweather.data.remote.WeatherServiceFactory
import io.reactivex.Observable

class WeatherCloudDataStore: WeatherDataStore {

    override fun getCurrentWeather(latitude: Double, longitude: Double, units: String): Observable<CurrentWeatherEntity> {
        return WeatherServiceFactory.makeService().currentWeather(latitude, longitude, units)
    }

    override fun getWeatherForecast(latitude: Double, longitude: Double, units: String): Observable<WeatherForecastEntity> {
        return WeatherServiceFactory.makeService().weatherForecast(latitude, longitude, units)
    }

}

