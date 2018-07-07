package com.example.hendratay.whatheweather.presentation.view.fragment

import com.example.hendratay.whatheweather.domain.interactor.GetTimeZone
import com.example.hendratay.whatheweather.domain.interactor.GetWeatherForecast
import com.example.hendratay.whatheweather.presentation.model.mapper.TimeZoneViewMapper
import com.example.hendratay.whatheweather.presentation.model.mapper.WeatherForecastViewMapper
import com.example.hendratay.whatheweather.presentation.viewmodel.TimeZoneViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
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
