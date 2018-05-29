package com.example.hendratay.whatheweather.presentation.view.activity

import com.example.hendratay.whatheweather.domain.interactor.GetCurrentWeather
import com.example.hendratay.whatheweather.presentation.model.mapper.CurrentWeatherViewMapper
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    // Anytime dagger tries to get an instance, it creates an instance of MainActivityModule
    // So we make @Provides function static
    // Now dagger could invoke MainActivityModule.provideCurrentWeatherViewModelFactory() without need to create new instance
    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideCurrentWeatherViewModelFactory(getCurrentWeather: GetCurrentWeather,
                                                  currentWeatherViewMapper: CurrentWeatherViewMapper):
                CurrentWeatherViewModelFactory {
            return CurrentWeatherViewModelFactory(getCurrentWeather, currentWeatherViewMapper)
        }

    }

}