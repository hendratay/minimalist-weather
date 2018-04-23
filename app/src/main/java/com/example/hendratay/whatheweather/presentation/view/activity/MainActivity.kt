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
import android.widget.Toast
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

    private var consolidatedList: MutableList<ListItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WhatheWeatherApplication.component.inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        rv_weather_forecast.layoutManager = LinearLayoutManager(this)
        adapter = ForecastAdapter(consolidatedList)
        rv_weather_forecast.adapter = adapter

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
                    weather_icon_image_view.setImageResource(WeatherIcon.getWeatherId(it?.weatherList?.get(0)!!.id,
                            it?.weatherList?.get(0).icon))
                    weather_desc_text_view.text = it?.weatherList?.get(0)?.description?.toUpperCase()

                    humidity_text_view.text = it?.main?.humidity.toString() + " %"
                    cloud_text_view.text = it?.clouds?.cloudiness.toString()
                })

        weatherForecastViewModel.getWeatherForecast().observe(this,
                Observer<WeatherForecastView> {
                    val groupedHashMap: HashMap<String, MutableList<ForecastView>> = groupDataIntoHashMap(it!!.forecastList.toMutableList())
                    for(forecastDate: String in groupedHashMap.keys) {
                        val dateItem = DateItem(forecastDate)
                        consolidatedList.add(dateItem)
                        for (forecast: ForecastView in groupedHashMap.get(forecastDate)!!) {
                            val generalItem = GeneralItem(forecast)
                            consolidatedList.add(generalItem)
                        }
                    }
                    adapter.notifyDataSetChanged()
                })
    }

    private fun groupDataIntoHashMap(forecastList: MutableList<ForecastView>): HashMap<String, MutableList<ForecastView>> {
        val groupedHashMap: HashMap<String, MutableList<ForecastView>> = HashMap()
        for(forecastView: ForecastView in forecastList) {
            val sdf = SimpleDateFormat("EEEE, d MMMM y")
            val date = sdf.format(Date(forecastView.dateTime * 1000))
            if(groupedHashMap.containsKey(date)) {
                groupedHashMap.get(date)?.add(forecastView)
            } else {
                val list: MutableList<ForecastView> = ArrayList()
                list.add(forecastView)
                groupedHashMap.put(date, list)
            }
        }
        return groupedHashMap
    }

}
