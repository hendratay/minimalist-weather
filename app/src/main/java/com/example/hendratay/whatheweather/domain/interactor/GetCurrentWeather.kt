package com.example.hendratay.whatheweather.domain.interactor

import com.example.hendratay.whatheweather.domain.model.CurrentWeather
import com.example.hendratay.whatheweather.domain.repository.WeatherRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetCurrentWeather @Inject constructor(val weatherRepository: WeatherRepository): UseCase<CurrentWeather, GetCurrentWeather.Params>() {

    override fun buildUseCaseObservable(params: Params?): Observable<CurrentWeather> {
        return weatherRepository.getCurrentWeather(params!!.latitude, params!!.longitude)
    }

    class Params(val latitude: Double, val longitude: Double) {

        companion object {
            fun forLocation(latitude: Double, longitude: Double): Params {
                return Params(latitude, longitude)
            }
        }

    }

}