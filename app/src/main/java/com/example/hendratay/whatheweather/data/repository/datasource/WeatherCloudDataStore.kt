package com.example.hendratay.whatheweather.data.repository.datasource

import com.example.hendratay.whatheweather.data.entity.WeatherForecastEntity
import com.example.hendratay.whatheweather.data.entity.CurrentWeatherEntity
import com.example.hendratay.whatheweather.data.remote.WeatherServiceFactory
import io.reactivex.Observable

class WeatherCloudDataStore: WeatherDataStore {

    override fun getCurrentWeather(cityName: String): Observable<CurrentWeatherEntity> {
        return WeatherServiceFactory.makeService().currentWeather(cityName)
    }

    override fun getWeatherForecast(cityName: String): Observable<WeatherForecastEntity> {
        return WeatherServiceFactory.makeService().weatherForecast(cityName)
    }

}

