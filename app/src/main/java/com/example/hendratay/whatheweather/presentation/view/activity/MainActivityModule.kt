package com.example.hendratay.whatheweather.presentation.view.activity

import com.example.hendratay.whatheweather.domain.interactor.GetCurrentWeather
import com.example.hendratay.whatheweather.domain.interactor.GetWeatherForecast
import com.example.hendratay.whatheweather.presentation.model.mapper.CurrentWeatherViewMapper
import com.example.hendratay.whatheweather.presentation.model.mapper.WeatherForecastViewMapper
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideCurrentWeatherViewModelFactory(getCurrentWeather: GetCurrentWeather,
                                                  currentWeatherViewMapper: CurrentWeatherViewMapper):
                CurrentWeatherViewModelFactory {
            return CurrentWeatherViewModelFactory(getCurrentWeather, currentWeatherViewMapper)
        }


        @JvmStatic
        @Provides
        fun provideWeatherForecastViewModelFactory(getWeatherForecast: GetWeatherForecast,
                                                   weatherForecastViewMapper: WeatherForecastViewMapper):
                WeatherForecastViewModelFactory {
            return WeatherForecastViewModelFactory(getWeatherForecast, weatherForecastViewMapper)
        }

    }

}
