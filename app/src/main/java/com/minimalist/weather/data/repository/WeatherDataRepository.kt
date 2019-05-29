package com.minimalist.weather.data.repository

import com.minimalist.weather.data.entity.mapper.CurrentWeatherMapper
import com.minimalist.weather.data.entity.mapper.WeatherForecastMapper
import com.minimalist.weather.domain.repository.WeatherRepository
import com.minimalist.weather.data.repository.datasource.WeatherDataStoreFactory
import com.minimalist.weather.domain.model.CurrentWeather
import com.minimalist.weather.domain.model.WeatherForecast
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
