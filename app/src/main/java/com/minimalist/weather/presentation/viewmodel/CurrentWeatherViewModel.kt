package com.minimalist.weather.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minimalist.weather.domain.interactor.DefaultObserver
import com.minimalist.weather.domain.interactor.GetCurrentWeather
import com.minimalist.weather.domain.model.CurrentWeather
import com.minimalist.weather.presentation.data.Resource
import com.minimalist.weather.presentation.data.ResourceState
import com.minimalist.weather.presentation.model.CurrentWeatherView
import com.minimalist.weather.presentation.model.mapper.CurrentWeatherViewMapper
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(val getCurrentWeather: GetCurrentWeather,
                                                  val currentWeatherViewMapper: CurrentWeatherViewMapper):
        ViewModel() {

    private val weatherLiveData: MutableLiveData<Resource<CurrentWeatherView>> = MutableLiveData()
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var units: String = ""

    override fun onCleared() {
        getCurrentWeather.dispose()
        super.onCleared()
    }

    fun getCurrentWeather() = weatherLiveData

    fun setLocation(lat: Double?, lng: Double?, units: String) {
        latitude = lat
        longitude =  lng
        this.units = units
        if(latitude == null && longitude == null) {
            weatherLiveData.postValue(Resource(ResourceState.ERROR, null, null))
        } else {
            fetchCurrentWeather()
        }
    }

    private fun fetchCurrentWeather() {
        weatherLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getCurrentWeather.execute(CurrentWeatherObserver(), GetCurrentWeather.Params.forLocation(latitude!!, longitude!!, units))
    }

    inner class CurrentWeatherObserver: DefaultObserver<CurrentWeather>() {

        override fun onComplete() {

        }

        override fun onNext(t: CurrentWeather) {
            weatherLiveData.postValue(Resource(ResourceState.SUCCESS, currentWeatherViewMapper.mapToView(t), null))
            Log.d("Current Weather", t.toString())
        }

        override fun onError(e: Throwable) {
            weatherLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
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