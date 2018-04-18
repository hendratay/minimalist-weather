package com.example.hendratay.whatheweather.presentation.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.data.entity.mapper.*
import com.example.hendratay.whatheweather.data.repository.WeatherDataRepository
import com.example.hendratay.whatheweather.data.repository.datasource.WeatherDataStoreFactory
import com.example.hendratay.whatheweather.domain.interactor.GetWeatherForecast
import com.example.hendratay.whatheweather.domain.interactor.GetCurrentWeather
import com.example.hendratay.whatheweather.presentation.model.CurrentWeatherView
import com.example.hendratay.whatheweather.presentation.model.WeatherForecastView
import com.example.hendratay.whatheweather.presentation.model.mapper.*
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel
    private lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    private lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
    }

    fun initComponents() {
        val weatherDataStoreFactory = WeatherDataStoreFactory()
        val currentWeatherMapper = CurrentWeatherMapper(CoordinateMapper(), WeatherMapper(), MainMapper(), WindMapper(), CloudMapper(), RainMapper(), SnowMapper(), SysMapper())
        val weatherForecastMapper = WeatherForecastMapper(CityMapper(CoordinateMapper()), ForecastMapper(MainMapper(), WeatherMapper(), CloudMapper(), WindMapper(), RainMapper(), SnowMapper()))
        val weatherDataRepository = WeatherDataRepository(weatherDataStoreFactory, currentWeatherMapper, weatherForecastMapper)

        val getCurrentWeather = GetCurrentWeather(weatherDataRepository)
        val currentWeatherViewMapper = CurrentWeatherViewMapper(CoordinateViewMapper(), WeatherViewMapper(), MainViewMapper(), WindViewMapper(), CloudViewMapper(), RainViewMapper(), SnowViewMapper(), SysViewMapper())
        currentWeatherViewModelFactory = CurrentWeatherViewModelFactory(getCurrentWeather, currentWeatherViewMapper)
        currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)

        val getWeatherForecast = GetWeatherForecast(weatherDataRepository)
        val weatherForecastViewMapper = WeatherForecastViewMapper(CityViewMapper(CoordinateViewMapper()), ForecastViewMapper(MainViewMapper(), WeatherViewMapper(), CloudViewMapper(), WindViewMapper(), RainViewMapper(), SnowViewMapper()))
        weatherForecastViewModelFactory = WeatherForecastViewModelFactory(getWeatherForecast, weatherForecastViewMapper)
        weatherForecastViewModel = ViewModelProviders.of(this, weatherForecastViewModelFactory).get(WeatherForecastViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        currentWeatherViewModel.getCurrentWeather().observe(this,
                Observer<CurrentWeatherView> {

                })

        weatherForecastViewModel.getWeatherForecast().observe(this,
                Observer<WeatherForecastView> {

                })
    }

}
