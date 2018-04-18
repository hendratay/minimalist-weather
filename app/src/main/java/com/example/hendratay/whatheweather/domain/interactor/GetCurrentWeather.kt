package com.example.hendratay.whatheweather.domain.interactor

import com.example.hendratay.whatheweather.domain.model.CurrentWeather
import com.example.hendratay.whatheweather.domain.repository.WeatherRepository
import io.reactivex.Observable

class GetCurrentWeather(val weatherRepository: WeatherRepository): UseCase<CurrentWeather, GetCurrentWeather.Params>() {

    override fun buildUseCaseObservable(params: Params?): Observable<CurrentWeather> {
        return weatherRepository.getCurrentWeather(params!!.cityName)
    }

    class Params(val cityName: String) {

        companion object {
            fun forCity(cityName: String): Params {
                return Params(cityName)
            }
        }

    }

}