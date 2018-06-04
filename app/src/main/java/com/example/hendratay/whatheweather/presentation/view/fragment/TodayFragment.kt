package com.example.hendratay.whatheweather.presentation.view.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.data.Resource
import com.example.hendratay.whatheweather.presentation.data.ResourceState
import com.example.hendratay.whatheweather.presentation.model.CurrentWeatherView
import com.example.hendratay.whatheweather.presentation.model.ForecastView
import com.example.hendratay.whatheweather.presentation.model.WeatherForecastView
import com.example.hendratay.whatheweather.presentation.view.activity.MainActivity
import com.example.hendratay.whatheweather.presentation.view.adapter.ForecastAdapter
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_today.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class TodayFragment: Fragment() {

    companion object {
        private val TAG = TodayFragment::class.simpleName
    }

    @Inject lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel
    private lateinit var adapter: ForecastAdapter
    private var forecastList: MutableList<ForecastView> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        setupSwipeRefresh()
        setupEmptyErrorButtonClick()

        currentWeatherViewModel = ViewModelProviders.of(activity as MainActivity, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
        // using `activity as MainActivity` because it can share between fragment
        weatherForecastViewModel = ViewModelProviders.of(activity as MainActivity, weatherForecastViewModelFactory).get(WeatherForecastViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        getCurrentWeather()
        getTodayWeatherForecast()
    }

    private fun setupRecyclerView() {
        rv_weather_forecast.layoutManager = LinearLayoutManager(activity as MainActivity, LinearLayoutManager.HORIZONTAL, false)
        adapter = ForecastAdapter(forecastList)
        rv_weather_forecast.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        swipe_refresh_layout.setOnRefreshListener {
            checkGpsEnabled()
        }
    }

    private fun setupEmptyErrorButtonClick() {
        empty_button.setOnClickListener {
            checkGpsEnabled()
        }
        error_button.setOnClickListener {
            checkGpsEnabled()
        }
        search_button.setOnClickListener {
            (activity as MainActivity).autoCompleteIntent()
        }
    }

    private fun checkGpsEnabled() {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            (activity as MainActivity).startLocationUpdates()
        } else {
            currentWeatherViewModel.setLatLng(0.0, 0.0)
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun getCurrentWeather() {
        currentWeatherViewModel.getCurrentWeather().observe(this,
                Observer<Resource<CurrentWeatherView>> {
                    it?.let { handleCurrentWeatherViewState(it.status, it.data, it.message) }
                })
    }

    private fun handleCurrentWeatherViewState(resourceState: ResourceState, data: CurrentWeatherView?, message: String?) {
        when(resourceState) {
            // because the data is small, so we remove the loading state
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    private fun setupScreenForLoadingState() {
        progress_bar.visibility = View.VISIBLE
        data_view.visibility = View.GONE
        activity?.city_name_text_view?.visibility = View.GONE
        empty_view.visibility = View.GONE
        error_view.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun setupScreenForSuccess(it: CurrentWeatherView?) {
        progress_bar.visibility = View.GONE
        error_view.visibility = View.GONE
        if (it != null) {
            weather_icon_image_view.setImageResource(WeatherIcon.getWeatherId(it.weatherList[0].id, it.weatherList[0].icon))
            temp_text_view.text = it.main.temp.roundToInt().toString() + " \u2103"
            weather_desc_text_view.text = it.weatherList[0].description.toUpperCase()
            pressure_text_view.text = "${it.main.pressure.roundToInt()} hPa"
            humidity_text_view.text = "${it.main.humidity}  %"
            cloud_text_view.text = it.clouds.cloudiness.toString() + " %"
            val tempMin = it.main.tempMin.toString() + " \u2103"
            val tempMax = it.main.tempMax.toString() + " \u2103"
            min_max_temp_text_view.text = "$tempMin / $tempMax"
            wind_text_view.text = it.wind.speed.toString() + " m/s"
            val sunrise = it.sys.sunriseTime
            val sunset = it.sys.sunsetTime
            val sdf = SimpleDateFormat("H:mm", Locale.getDefault())
            val sunriseTime = sdf.format(Date(sunrise * 1000))
            val sunsetTime = sdf.format(Date(sunset * 1000))
            sunrise_sunset_text_view.text = "$sunriseTime / $sunsetTime"

            activity?.city_name_text_view?.visibility = View.VISIBLE
            data_view.visibility = View.VISIBLE
            (activity as MainActivity).supportActionBar?.show()
            activity?.city_name_text_view?.visibility = View.VISIBLE
            activity?.weekly?.visibility = View.VISIBLE
            swipe_refresh_layout.isRefreshing = false
        } else {
            empty_view.visibility = View.VISIBLE
        }
    }

    private fun setupScreenForError(message: String?) {
        progress_bar.visibility = View.GONE
        data_view.visibility = View.GONE
        empty_view.visibility = View.GONE
        error_view.visibility = View.VISIBLE
        (activity as MainActivity).supportActionBar?.hide()
        activity?.city_name_text_view?.visibility = View.GONE
        activity?.weekly?.visibility = View.GONE
        Log.e(TAG, message)
    }

    private fun getTodayWeatherForecast() {
        weatherForecastViewModel.getWeatherForecast().observe(this,
                Observer<Resource<WeatherForecastView>> {
                    it?.let { handleWeatherForecastState(it.status, it.data, it.message) }
                })
    }

    private fun handleWeatherForecastState(resourceState: ResourceState, data: WeatherForecastView?, message: String?) {
        when(resourceState) {
            ResourceState.LOADING -> setupRecyclerForLoadingState()
            ResourceState.SUCCESS -> setupRecyclerForSuccess(data)
            ResourceState.ERROR -> setupRecyclerForError(message)
        }
    }

    private fun setupRecyclerForLoadingState() {
    }

    private fun setupRecyclerForSuccess(it: WeatherForecastView?) {
        it?.let {
            forecastList.clear()
            it.forecastList.forEach {
                val sdf = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
                val dateTime = sdf.format(Date(it.dateTime * 1000))
                val currentDateTime = sdf.format(Calendar.getInstance().time)
                if (dateTime == currentDateTime) {
                    forecastList.add(it)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerForError(message: String?) {
        Log.e(TAG, message)
    }

}