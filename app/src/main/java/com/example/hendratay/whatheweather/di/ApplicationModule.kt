package com.example.hendratay.whatheweather.di

import android.app.Application
import android.content.Context
import com.example.hendratay.whatheweather.data.entity.mapper.CurrentWeatherMapper
import com.example.hendratay.whatheweather.data.entity.mapper.TimeZoneMapper
import com.example.hendratay.whatheweather.data.entity.mapper.WeatherForecastMapper
import com.example.hendratay.whatheweather.data.repository.TimeZoneDataRepository
import com.example.hendratay.whatheweather.data.repository.WeatherDataRepository
import com.example.hendratay.whatheweather.data.repository.datasource.TimeZoneDataStoreFactory
import com.example.hendratay.whatheweather.data.repository.datasource.WeatherDataStoreFactory
import com.example.hendratay.whatheweather.domain.repository.TimeZoneRepository
import com.example.hendratay.whatheweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideWeatherRepository(weatherDataStoreFactory: WeatherDataStoreFactory,
                                 currentWeatherMapper: CurrentWeatherMapper,
                                 weatherForecastMapper: WeatherForecastMapper): WeatherRepository {
        return WeatherDataRepository(weatherDataStoreFactory, currentWeatherMapper, weatherForecastMapper)
    }

    @Provides
    fun provideTimeZoneRepository(timeZoneDataStoreFactory: TimeZoneDataStoreFactory,
                                  timeZoneMapper: TimeZoneMapper): TimeZoneRepository {
        return TimeZoneDataRepository(timeZoneDataStoreFactory, timeZoneMapper)
    }

}