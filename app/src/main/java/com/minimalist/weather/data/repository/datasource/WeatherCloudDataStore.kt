package com.minimalist.weather.data.repository.datasource

import com.minimalist.weather.data.entity.WeatherForecastEntity
import com.minimalist.weather.data.entity.CurrentWeatherEntity
import com.minimalist.weather.data.remote.WeatherServiceFactory
import io.reactivex.Observable

class WeatherCloudDataStore: WeatherDataStore {

    override fun getCurrentWeather(latitude: Double, longitude: Double, units: String): Observable<CurrentWeatherEntity> {
        return WeatherServiceFactory.makeService().currentWeather(latitude, longitude, units)
    }

    override fun getWeatherForecast(latitude: Double, longitude: Double, units: String): Observable<WeatherForecastEntity> {
        return WeatherServiceFactory.makeService().weatherForecast(latitude, longitude, units)
    }

}

