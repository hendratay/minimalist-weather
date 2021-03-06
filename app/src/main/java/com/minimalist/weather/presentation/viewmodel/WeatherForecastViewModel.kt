package com.minimalist.weather.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minimalist.weather.domain.interactor.DefaultObserver
import com.minimalist.weather.domain.interactor.GetWeatherForecast
import com.minimalist.weather.domain.model.WeatherForecast
import com.minimalist.weather.presentation.data.Resource
import com.minimalist.weather.presentation.data.ResourceState
import com.minimalist.weather.presentation.model.mapper.WeatherForecastViewMapper
import com.minimalist.weather.presentation.model.WeatherForecastView
import javax.inject.Inject

class WeatherForecastViewModel @Inject constructor(val getWeatherForecast: GetWeatherForecast,
                                                   val weatherForecastViewMapper: WeatherForecastViewMapper):
        ViewModel() {

    private val forecastLiveData: MutableLiveData<Resource<WeatherForecastView>> = MutableLiveData()
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var units: String = ""

    override fun onCleared() {
        getWeatherForecast.dispose()
        super.onCleared()
    }

    fun getWeatherForecast() = forecastLiveData

    fun setLocation(lat: Double?, lng: Double?, units: String) {
        latitude = lat
        longitude =  lng
        this.units = units
        if(latitude == null && longitude == null) {
            forecastLiveData.postValue(Resource(ResourceState.ERROR, null, null))
        } else {
            fetchWeatherForecast()
        }
    }

    fun fetchWeatherForecast() {
        forecastLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getWeatherForecast.execute(WeatherForecastObserver(), GetWeatherForecast.Params.forLocation(latitude!!, longitude!!, units))
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
