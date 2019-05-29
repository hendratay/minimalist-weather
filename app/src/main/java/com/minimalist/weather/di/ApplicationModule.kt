package com.minimalist.weather.di

import android.app.Application
import android.content.Context
import com.minimalist.weather.data.entity.mapper.CurrentWeatherMapper
import com.minimalist.weather.data.entity.mapper.TimeZoneMapper
import com.minimalist.weather.data.entity.mapper.WeatherForecastMapper
import com.minimalist.weather.data.repository.TimeZoneDataRepository
import com.minimalist.weather.data.repository.WeatherDataRepository
import com.minimalist.weather.data.repository.datasource.TimeZoneDataStoreFactory
import com.minimalist.weather.data.repository.datasource.WeatherDataStoreFactory
import com.minimalist.weather.domain.repository.TimeZoneRepository
import com.minimalist.weather.domain.repository.WeatherRepository
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