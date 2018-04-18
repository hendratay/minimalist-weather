package com.example.hendratay.whatheweather.presentation.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.WhatheWeatherApplication
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
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WhatheWeatherApplication.component.inject(this)

        currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
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
