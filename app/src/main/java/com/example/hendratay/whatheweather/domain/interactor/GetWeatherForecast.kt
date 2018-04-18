package com.example.hendratay.whatheweather.domain.interactor

import com.example.hendratay.whatheweather.domain.model.WeatherForecast
import com.example.hendratay.whatheweather.domain.repository.WeatherRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetWeatherForecast @Inject constructor(val weatherRepository: WeatherRepository): UseCase<WeatherForecast, GetWeatherForecast.Params>() {

    override fun buildUseCaseObservable(params: Params?): Observable<WeatherForecast> {
        return weatherRepository.getWeatherForecast(params!!.cityName)
    }


    class Params(val cityName: String) {

        companion object {
            fun forCity(cityName: String): Params {
                return Params(cityName)
            }
        }

    }

}

