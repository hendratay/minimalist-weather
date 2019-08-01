package com.minimalist.weather.domain.interactor

import com.minimalist.weather.domain.model.CurrentWeather
import com.minimalist.weather.domain.repository.WeatherRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetCurrentWeather @Inject constructor(private val weatherRepository: WeatherRepository): UseCase<CurrentWeather, GetCurrentWeather.Params>() {

    override fun buildUseCaseObservable(params: Params?): Observable<CurrentWeather> {
        return weatherRepository.getCurrentWeather(params!!.latitude, params.longitude, params.units)
    }

    class Params(val latitude: Double, val longitude: Double, val units: String) {

        companion object {
            fun forLocation(latitude: Double, longitude: Double, units: String): Params {
                return Params(latitude, longitude, units)
            }
        }

    }

}