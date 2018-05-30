package com.example.hendratay.whatheweather.domain.interactor

import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import com.example.hendratay.whatheweather.domain.repository.WeatherRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetWeatherForecast @Inject constructor(val weatherRepository: WeatherRepository): UseCase<WeatherForecast, GetWeatherForecast.Params>() {

    override fun buildUseCaseObservable(params: Params?): Observable<WeatherForecast> {
        return weatherRepository.getWeatherForecast(params!!.latitude, params!!.longitude)
    }


    class Params(val latitude: Double, val longitude: Double) {

        companion object {
            fun forLocation(latitude: Double, longitude: Double): Params {
                return Params(latitude, longitude)
            }
        }

    }

}

