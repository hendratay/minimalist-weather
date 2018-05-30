package com.example.hendratay.whatheweather.presentation.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.example.hendratay.whatheweather.domain.interactor.DefaultObserver
import com.example.hendratay.whatheweather.domain.interactor.GetWeatherForecast
import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import com.example.hendratay.whatheweather.presentation.model.mapper.WeatherForecastViewMapper
import com.example.hendratay.whatheweather.presentation.model.WeatherForecastView
import javax.inject.Inject

class WeatherForecastViewModel @Inject constructor(val getWeatherForecast: GetWeatherForecast,
                                                   val weatherForecastViewMapper: WeatherForecastViewMapper):
        ViewModel() {

    private val forecastLiveData: MutableLiveData<WeatherForecastView> = MutableLiveData()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCleared() {
        getWeatherForecast.dispose()
        super.onCleared()
    }

    fun getWeatherForecast() = forecastLiveData

    fun setlatLng(newLatLng: String) {
        latitude = newLatLng.split(",")[0].toDouble()
        longitude =  newLatLng.split(",")[1].toDouble()
        fetchWeatherForecast()
    }

    fun fetchWeatherForecast() = getWeatherForecast.execute(WeatherForecastObserver(), GetWeatherForecast.Params.forLocation(latitude, longitude))

    inner class WeatherForecastObserver: DefaultObserver<WeatherForecast>() {

        override fun onComplete() {

        }

        override fun onNext(t: WeatherForecast) {
            forecastLiveData.postValue(weatherForecastViewMapper.mapToView(t))
            Log.d("Weather Forecast", t.toString())
        }

        override fun onError(e: Throwable) {
            Log.d("Weather Forecast", e.toString())
        }

    }

}

open class WeatherForecastViewModelFactory(
        private val getWeatherForecast: GetWeatherForecast,
        private val weatherForecastViewMapper: WeatherForecastViewMapper): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherForecastViewModel::class.java)) {
            return WeatherForecastViewModel(getWeatherForecast, weatherForecastViewMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
