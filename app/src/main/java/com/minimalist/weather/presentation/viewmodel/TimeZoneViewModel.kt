package com.minimalist.weather.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minimalist.weather.domain.interactor.GetTimeZone
import com.minimalist.weather.domain.interactor.SingleObserver
import com.minimalist.weather.domain.model.TimeZone
import com.minimalist.weather.presentation.model.TimeZoneView
import com.minimalist.weather.presentation.model.mapper.TimeZoneViewMapper
import javax.inject.Inject

class TimeZoneViewModel @Inject constructor(private val getTimeZone: GetTimeZone,
                                            val timeZoneViewMapper: TimeZoneViewMapper):
        ViewModel() {

    private val timeZoneLiveData: MutableLiveData<TimeZoneView> = MutableLiveData()
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCleared() {
        getTimeZone.dispose()
        super.onCleared()
    }

    fun getTimeZone() = timeZoneLiveData

    fun setQuery(lat: Double?, lng: Double?) {
        latitude = lat
        longitude = lng
        fetchTimeZone()
    }

    private fun fetchTimeZone() {
        getTimeZone.execute(TimeZoneObserver(), GetTimeZone.Params.forTimeZone("$latitude,$longitude", System.currentTimeMillis()/1000))
    }

    inner class TimeZoneObserver: SingleObserver<TimeZone>() {

        override fun onSuccess(t: TimeZone) {
            timeZoneLiveData.postValue(timeZoneViewMapper.mapToView(t))
            Log.d("Time Zone", t.toString())
        }

        override fun onError(e: Throwable) {
            Log.d("Time Zone", e.toString())
        }

    }

}

open class TimeZoneViewModelFactory(
        private val getTimeZone: GetTimeZone,
        private val timeZoneViewMapper: TimeZoneViewMapper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimeZoneViewModel::class.java)) {
            return TimeZoneViewModel(getTimeZone, timeZoneViewMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}