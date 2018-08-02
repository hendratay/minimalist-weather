package com.example.hendratay.whatheweather.data.repository

import com.example.hendratay.whatheweather.data.entity.mapper.CurrentWeatherMapper
import com.example.hendratay.whatheweather.data.entity.mapper.WeatherForecastMapper
import com.example.hendratay.whatheweather.domain.repository.WeatherRepository
import com.example.hendratay.whatheweather.data.repository.datasource.WeatherDataStoreFactory
import com.example.hendratay.whatheweather.domain.model.CurrentWeather
import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import io.reactivex.Observable
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(val factory: WeatherDataStoreFactory,
                                                private val currentWeatherMapper: CurrentWeatherMapper,
                                                private val weatherForecastMapper: WeatherForecastMapper):
        WeatherRepository {

    override fun getCurrentWeather(latitude: Double, longitude: Double, units: String): Observable<CurrentWeather> {
        return factory.create().getCurrentWeather(latitude, longitude, units)
                .map { currentWeatherMapper.mapFromEntity(it) }
    }

    override fun getWeatherForecast(latitude: Double, longitude: Double, units: String): Observable<WeatherForecast> {
        return factory.create().getWeatherForecast(latitude, longitude, units)
                .map { weatherForecastMapper.mapFromEntity(it) }
    }

}
