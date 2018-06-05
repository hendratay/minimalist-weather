package com.example.hendratay.whatheweather.presentation.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.example.hendratay.whatheweather.domain.interactor.DefaultObserver
import com.example.hendratay.whatheweather.domain.interactor.GetWeatherForecast
import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import com.example.hendratay.whatheweather.presentation.data.Resource
import com.example.hendratay.whatheweather.presentation.data.ResourceState
import com.example.hendratay.whatheweather.presentation.model.mapper.WeatherForecastViewMapper
import com.example.hendratay.whatheweather.presentation.model.WeatherForecastView
import javax.inject.Inject

class WeatherForecastViewModel @Inject constructor(val getWeatherForecast: GetWeatherForecast,
                                                   val weatherForecastViewMapper: WeatherForecastViewMapper):
        ViewModel() {

    private val forecastLiveData: MutableLiveData<Resource<WeatherForecastView>> = MutableLiveData()
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCleared() {
        getWeatherForecast.dispose()
        super.onCleared()
    }

    fun getWeatherForecast() = forecastLiveData

    fun setlatLng(lat: Double?, lng: Double?) {
        latitude = lat
        longitude =  lng
        if(latitude == null && longitude == null) {
            forecastLiveData.postValue(Resource(ResourceState.ERROR, null, "No Location Provided"))
        } else {
            fetchWeatherForecast()
        }
    }

    fun fetchWeatherForecast() {
        forecastLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getWeatherForecast.execute(WeatherForecastObserver(), GetWeatherForecast.Params.forLocation(latitude!!, longitude!!))
    }

    inner class WeatherForecastObserver: DefaultObserver<WeatherForecast>() {

        override fun onComplete() {

        }

        override fun onNext(t: WeatherForecast) {
            forecastLiveData.postValue(Resource(ResourceState.SUCCESS, weatherForecastViewMapper.mapToView(t), null))
            Log.d("Weather Forecast", t.toString())
        }

        override fun onError(e: Throwable) {
            forecastLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
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
