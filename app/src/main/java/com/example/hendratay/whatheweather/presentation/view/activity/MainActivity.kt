package com.example.hendratay.whatheweather.presentation.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.WhatheWeatherApplication
import com.example.hendratay.whatheweather.presentation.model.CurrentWeatherView
import com.example.hendratay.whatheweather.presentation.model.ForecastView
import com.example.hendratay.whatheweather.presentation.model.WeatherForecastView
import com.example.hendratay.whatheweather.presentation.view.adapter.ForecastAdapter
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.math.roundToInt
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.hendratay.whatheweather.presentation.view.adapter.DividerItemDecorator
import com.example.hendratay.whatheweather.presentation.view.adapter.DivideritemDecoratorVertical
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon


class MainActivity : AppCompatActivity() {

    @Inject lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel

    private var forecastList: List<ForecastView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WhatheWeatherApplication.component.inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        rv_weather_forecast.layoutManager = GridLayoutManager(this, 3)
        val dividerItemDecoration = DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider)!!)
        val dividerItemDecorationVertical = DivideritemDecoratorVertical(ContextCompat.getDrawable(this, R.drawable.divider)!!)
        rv_weather_forecast.addItemDecoration(dividerItemDecoration)
        rv_weather_forecast.addItemDecoration(dividerItemDecorationVertical)
        rv_weather_forecast.adapter = ForecastAdapter(forecastList)

        currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
        weatherForecastViewModel = ViewModelProviders.of(this, weatherForecastViewModelFactory).get(WeatherForecastViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.refresh -> {
                //TODO: REFRESH CURRENT DATA AND WEATHER FORECAST
                Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> { return super.onOptionsItemSelected(item) }
        }
    }

    override fun onStart() {
        super.onStart()
        currentWeatherViewModel.getCurrentWeather().observe(this,
                Observer<CurrentWeatherView> {
                    city_name_text_view.text = it?.cityName?.toUpperCase()

                    temp_text_view.text = it?.main?.temp?.roundToInt().toString() + " \u2103"
                    pressure_text_view.text = it?.main?.pressure?.roundToInt().toString()

                    weather_condition_image_view.setImageResource(WeatherIcon.getWeatherId(it?.weatherList?.get(0)!!.id,
                            it?.weatherList?.get(0).icon))
                    weather_condition_text_view.text = it?.weatherList?.get(0)?.description?.toUpperCase()

                    humidity_text_view.text = it?.main?.humidity.toString() + " %"
                    wind_text_view.text = it?.wind?.speed.toString()
                    cloud_text_view.text = it?.clouds?.cloudiness.toString()
                })

        weatherForecastViewModel.getWeatherForecast().observe(this,
                Observer<WeatherForecastView> {
                    forecastList = it!!.forecastList
                    rv_weather_forecast.adapter = ForecastAdapter(forecastList)
                })
    }

}
