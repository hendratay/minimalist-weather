package com.example.hendratay.whatheweather.data.remote

import com.example.hendratay.whatheweather.data.entity.WeatherForecastEntity
import com.example.hendratay.whatheweather.data.entity.CurrentWeatherEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    //Todo: currentWeather from lat and lng
    @GET("weather")
    fun currentWeather(@Query("q") cityName: String): Observable<CurrentWeatherEntity>

    //Todo: weatherForecast from lat and lng
    @GET("forecast")
    fun weatherForecast(@Query("q") cityName: String): Observable<WeatherForecastEntity>

}