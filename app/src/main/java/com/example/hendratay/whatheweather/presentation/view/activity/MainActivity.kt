package com.example.hendratay.whatheweather.presentation.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.model.*
import com.example.hendratay.whatheweather.presentation.view.adapter.ForecastAdapter
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import dagger.android.AndroidInjection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlinx.android.synthetic.main.activity_main.*

// Todo: add swipe to refresh view

const val TAG = "MainActivity"
const val REQUEST_ACCESS_FINE_LOCATION = 111
const val REQUEST_CHECK_SETTINGS = 222
class MainActivity : AppCompatActivity() {

    @Inject lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel
    private lateinit var adapter: ForecastAdapter

    private var forecastList: MutableList<ForecastView> = ArrayList()
    // Todo: follow display a location address guide
    private lateinit var geocoder: Geocoder
    private var address: List<Address> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        setupToolbar()
        setupRecyclerView()

        checkLocationPermission()
        createLocationRequest()

        fusedLocationClient = provideFusedLocationClient()
        receiveLocationUpdates()
        startLocationUpdates()

        geocoder = Geocoder(this, Locale.getDefault())

        currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
        weatherForecastViewModel = ViewModelProviders.of(this, weatherForecastViewModelFactory).get(WeatherForecastViewModel::class.java)
    }

    fun provideFusedLocationClient(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(this)
    }

    private fun checkLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest().apply {
            interval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener {
            if (it is ResolvableApiException){
                try {
                    it.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.e(TAG, "Error at LocationSettingsResponse.addOnFailureListener")
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun receiveLocationUpdates() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                p0 ?: return
                for(location in p0.locations) {
                    currentWeatherViewModel.setlatLng("${location.latitude},${location.longitude}")
                    weatherForecastViewModel.setlatLng("${location.latitude},${location.longitude}")

                    // Todo: Follow display a location addresss guide at android develoepr
                    address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                }
                stopLocationUpdates()
            }
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
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
                    it?.let {
                        city_name_text_view.text = "${address[0].thoroughfare} \n".capitalize() +
                                "${address[0].locality}, ${address[0].countryName}".capitalize()

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
