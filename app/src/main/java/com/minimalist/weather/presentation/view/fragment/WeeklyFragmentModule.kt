package com.minimalist.weather.presentation.view.fragment

import com.minimalist.weather.domain.interactor.GetTimeZone
import com.minimalist.weather.domain.interactor.GetWeatherForecast
import com.minimalist.weather.presentation.model.mapper.TimeZoneViewMapper
import com.minimalist.weather.presentation.model.mapper.WeatherForecastViewMapper
import com.minimalist.weather.presentation.viewmodel.TimeZoneViewModelFactory
import com.minimalist.weather.presentation.viewmodel.WeatherForecastViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class WeeklyFragmentModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideWeatherForecastViewModelFactory(getWeatherForecast: GetWeatherForecast,
                                                   weatherForecastViewMapper: WeatherForecastViewMapper):
                WeatherForecastViewModelFactory {
            return WeatherForecastViewModelFactory(getWeatherForecast, weatherForecastViewMapper)
        }

        @JvmStatic
        @Provides
        fun provideTimeZoneViewModelFactory(getTimeZone: GetTimeZone,
                                            timeZoneViewMapper: TimeZoneViewMapper):
                TimeZoneViewModelFactory {
            return TimeZoneViewModelFactory(getTimeZone, timeZoneViewMapper)
        }

    }

}
