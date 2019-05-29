package com.minimalist.weather.data.remote

import com.minimalist.weather.data.entity.WeatherForecastEntity
import com.minimalist.weather.data.entity.CurrentWeatherEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun currentWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("units") units: String): Observable<CurrentWeatherEntity>

    @GET("forecast")
    fun weatherForecast(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("units") units: String): Observable<WeatherForecastEntity>

}