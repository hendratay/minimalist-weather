package com.example.hendratay.whatheweather.presentation.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.WhatheWeatherApplication
import com.example.hendratay.whatheweather.presentation.view.adapter.ForecastAdapter
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.math.roundToInt
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.hendratay.whatheweather.presentation.model.*
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    @Inject lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel
    private lateinit var adapter: ForecastAdapter

    private var forecastList: MutableList<ForecastView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WhatheWeatherApplication.component.inject(this)

        setupToolbar()
        setupRecyclerView()

        currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
        weatherForecastViewModel = ViewModelProviders.of(this, weatherForecastViewModelFactory).get(WeatherForecastViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.refresh -> {
                currentWeatherViewModel.fetchCurrentWeather()
                return true
            }
            else -> { return super.onOptionsItemSelected(item) }
        }
    }

    override fun onStart() {
        super.onStart()
        getCurrentWeather()
        getTodayWeatherForecast()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupRecyclerView() {
        rv_weather_forecast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = ForecastAdapter(forecastList)
        rv_weather_forecast.adapter = adapter
    }

    private fun getCurrentWeather() {
        currentWeatherViewModel.getCurrentWeather().observe(this,
                Observer<CurrentWeatherView> {
                    if (it != null) {
                        city_name_text_view.text = it.cityName.toUpperCase()

                        weather_icon_image_view.setImageResource(WeatherIcon.getWeatherId(it.weatherList[0].id, it.weatherList[0].icon))
                        temp_text_view.text = it.main.temp.roundToInt().toString() + " \u2103"
                        weather_desc_text_view.text = it.weatherList[0].description.toUpperCase()

                        pressure_text_view.text = it.main.pressure.roundToInt().toString() + "hPa"
                        humidity_text_view.text = it.main.humidity.toString() + " %"
                        cloud_text_view.text = it.clouds.cloudiness.toString() + " %"

                        val tempMin = it.main.tempMin.toString() + " \u2103"
                        val tempMax = it.main.tempMax.toString() + " \u2103"
                        min_max_temp_text_view.text = "$tempMin / $tempMax"
                        wind_text_view.text = it.wind.speed.toString() + " m/s"
                        val sunrise = it.sys.sunriseTime
                        val sunset = it.sys.sunsetTime
                        val sdf = SimpleDateFormat("H:mm")
                        val sunriseTime = sdf.format(Date(sunrise * 1000))
                        val sunsetTime = sdf.format(Date(sunset * 1000))
                        sunrise_sunset_text_view.text = "$sunriseTime / $sunsetTime"
                    }
                })
    }

    private fun getTodayWeatherForecast() {
        weatherForecastViewModel.getWeatherForecast().observe(this,
                Observer<WeatherForecastView> {
                    forecastList.clear()
                    it?.forecastList?.forEach {
                        val sdf = SimpleDateFormat("dd MM yyyy")
                        val dateTime = sdf.format(Date(it.dateTime * 1000))
                        val currentDateTime = sdf.format(Calendar.getInstance().time)
                        if (dateTime == currentDateTime) {
                            forecastList.add(it)
                        }
                    }
                    adapter.notifyDataSetChanged()
                })
    }

}
