package com.example.hendratay.whatheweather.presentation.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.example.hendratay.whatheweather.domain.interactor.DefaultObserver
import com.example.hendratay.whatheweather.domain.interactor.GetCurrentWeather
import com.example.hendratay.whatheweather.domain.model.CurrentWeather
import com.example.hendratay.whatheweather.presentation.model.CurrentWeatherView
import com.example.hendratay.whatheweather.presentation.model.mapper.WeatherViewMapper
import com.example.hendratay.whatheweather.presentation.model.mapper.CurrentWeatherViewMapper

class CurrentWeatherViewModel(val getCurrentWeather: GetCurrentWeather,
                              val currentWeatherViewMapper: CurrentWeatherViewMapper):
        ViewModel() {

    private val weatherLiveData: MutableLiveData<CurrentWeatherView> = MutableLiveData()

    init {
        fetchCurrentWeather()
    }

    override fun onCleared() {
        getCurrentWeather.dispose()
        super.onCleared()
    }

    fun getCurrentWeather() = weatherLiveData

    fun fetchCurrentWeather() = getCurrentWeather.execute(CurrentWeatherObserver(), GetCurrentWeather.Params.forCity("Indonesia"))

    inner class CurrentWeatherObserver: DefaultObserver<CurrentWeather>() {

        override fun onComplete() {

        }

        override fun onNext(t: CurrentWeather) {
            currentWeatherViewMapper.mapToView(t)
            Log.d("Current Weather", t.toString())
        }

        override fun onError(e: Throwable) {
            Log.d("Current Weather", e.toString())
        }

    }

}

open class CurrentWeatherViewModelFactory(
        private val getCurrentWeather: GetCurrentWeather,
        private val currentWeatherViewMapper: CurrentWeatherViewMapper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
            return CurrentWeatherViewModel(getCurrentWeather, currentWeatherViewMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}