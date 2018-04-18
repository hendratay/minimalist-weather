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
                                                val currentWeatherMapper: CurrentWeatherMapper,
                                                val weatherForecastMapper: WeatherForecastMapper):
        WeatherRepository {

    override fun getCurrentWeather(cityName: String): Observable<CurrentWeather> {
        return factory.create().getCurrentWeather(cityName)
                .map { currentWeatherMapper.mapFromEntity(it) }
    }

    override fun getWeatherForecast(cityName: String): Observable<WeatherForecast> {
        return factory.create().getWeatherForecast(cityName)
                .map { weatherForecastMapper.mapFromEntity(it) }
    }

}
