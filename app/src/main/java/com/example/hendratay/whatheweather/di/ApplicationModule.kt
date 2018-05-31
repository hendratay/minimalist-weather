package com.example.hendratay.whatheweather.di

import android.app.Application
import android.content.Context
import com.example.hendratay.whatheweather.data.entity.mapper.CurrentWeatherMapper
import com.example.hendratay.whatheweather.data.entity.mapper.WeatherForecastMapper
import com.example.hendratay.whatheweather.data.repository.WeatherDataRepository
import com.example.hendratay.whatheweather.data.repository.datasource.WeatherDataStoreFactory
import com.example.hendratay.whatheweather.domain.interactor.GetCurrentWeather
import com.example.hendratay.whatheweather.domain.interactor.GetWeatherForecast
import com.example.hendratay.whatheweather.domain.repository.WeatherRepository
import com.example.hendratay.whatheweather.presentation.model.mapper.CurrentWeatherViewMapper
import com.example.hendratay.whatheweather.presentation.model.mapper.WeatherForecastViewMapper
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
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

}