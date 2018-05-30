package com.example.hendratay.whatheweather.data.remote

import com.example.hendratay.whatheweather.data.entity.WeatherForecastEntity
import com.example.hendratay.whatheweather.data.entity.CurrentWeatherEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun currentWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double): Observable<CurrentWeatherEntity>

    @GET("forecast")
    fun weatherForecast(@Query("lat") latitude: Double, @Query("lon") longitude: Double): Observable<WeatherForecastEntity>

}