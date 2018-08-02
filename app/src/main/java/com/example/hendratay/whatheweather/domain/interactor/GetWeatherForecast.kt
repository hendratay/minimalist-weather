package com.example.hendratay.whatheweather.domain.interactor

import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import com.example.hendratay.whatheweather.domain.repository.WeatherRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetWeatherForecast @Inject constructor(private val weatherRepository: WeatherRepository): UseCase<WeatherForecast, GetWeatherForecast.Params>() {

    override fun buildUseCaseObservable(params: Params?): Observable<WeatherForecast> {
        return weatherRepository.getWeatherForecast(params!!.latitude, params!!.longitude, params!!.units)
    }


    class Params(val latitude: Double, val longitude: Double, val units: String) {

        companion object {
            fun forLocation(latitude: Double, longitude: Double, units: String): Params {
                return Params(latitude, longitude, units)
            }
        }

    }

}
