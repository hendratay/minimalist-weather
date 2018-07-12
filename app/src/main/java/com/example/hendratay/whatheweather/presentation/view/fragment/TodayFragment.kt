package com.example.hendratay.whatheweather.presentation.view.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.data.Resource
import com.example.hendratay.whatheweather.presentation.data.ResourceState
import com.example.hendratay.whatheweather.presentation.model.CurrentWeatherView
import com.example.hendratay.whatheweather.presentation.model.ForecastView
import com.example.hendratay.whatheweather.presentation.model.TimeZoneView
import com.example.hendratay.whatheweather.presentation.model.WeatherForecastView
import com.example.hendratay.whatheweather.presentation.view.activity.MainActivity
import com.example.hendratay.whatheweather.presentation.view.adapter.ForecastAdapter
import com.example.hendratay.whatheweather.presentation.view.utils.TimeFormat
import com.example.hendratay.whatheweather.presentation.view.utils.WeatherIcon
import com.example.hendratay.whatheweather.presentation.viewmodel.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.error_view.view.*
import kotlinx.android.synthetic.main.fragment_today.*
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class TodayFragment: Fragment() {

    companion object {
        private val TAG = TodayFragment::class.simpleName
    }

    @Inject lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory
    @Inject lateinit var timeZoneViewModelFactory: TimeZoneViewModelFactory

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel
    private lateinit var timeZoneViewModel: TimeZoneViewModel
    private lateinit var adapter: ForecastAdapter
    private var forecastList: MutableList<ForecastView> = ArrayList()
    private var sunriseTime: Long? = null
    private var sunsetTime: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        setupWeeklyButton()
        setupSwipeRefresh()
        setupEmptyErrorButtonClick()

        // using `activity as MainActivity` because it can share between fragment
        currentWeatherViewModel = ViewModelProviders.of(activity as MainActivity, currentWeatherViewModelFactory).get(CurrentWeatherViewModel::class.java)
        weatherForecastViewModel = ViewModelProviders.of(activity as MainActivity, weatherForecastViewModelFactory).get(WeatherForecastViewModel::class.java)
        timeZoneViewModel = ViewModelProviders.of(activity as MainActivity, timeZoneViewModelFactory)[TimeZoneViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        getTimeZone()
        getCurrentWeather()
        getTodayWeatherForecast()
    }

    private fun setupRecyclerView() {
        rv_weather_forecast.layoutManager = LinearLayoutManager(activity as MainActivity, LinearLayoutManager.HORIZONTAL, false)
        adapter = ForecastAdapter(forecastList)
        rv_weather_forecast.adapter = adapter
    }

    private fun setupWeeklyButton() {
        weekly.setOnClickListener {
            (activity as MainActivity).loadFragment(WeeklyFragment())
        }
    }

    private fun setupSwipeRefresh() {
        swipe_refresh_layout.setOnRefreshListener {
            checkGpsEnabled()
        }
    }

    private fun setupEmptyErrorButtonClick() {
        empty_button.setOnClickListener {
            setupScreenForLoadingState()
            checkGpsEnabled()
        }
        error_button.setOnClickListener {
            setupScreenForLoadingState()
            checkGpsEnabled()
        }
        button_recycler_view.setOnClickListener {
            weatherForecastViewModel.fetchWeatherForecast()
        }
        location_button.setOnClickListener {
            if((activity as MainActivity).connectivityStatus()) {
                (activity as MainActivity).placePickerIntent()
            } else {
                Toast.makeText(activity, R.string.notice_no_network, Toast.LENGTH_SHORT).show()
                setupScreenForError()
            }
        }
    }

    private fun checkGpsEnabled() {
        if((activity as MainActivity).connectivityStatus()) {
            val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                (activity as MainActivity).startLocationUpdates()
            } else {
                (activity as MainActivity).createLocationRequest()
            }
        } else {
            Toast.makeText(activity, R.string.notice_no_network, Toast.LENGTH_SHORT).show()
            (activity as MainActivity).toolbar.visibility = View.GONE
            setupScreenForError()
        }
    }

    private fun getCurrentWeather() {
        currentWeatherViewModel.getCurrentWeather().observe(this,
                Observer<Resource<CurrentWeatherView>> {
                    if(view?.parent != null) {
                        it?.let { handleCurrentWeatherViewState(it.status, it.data) }
                    }
                })
    }

    private fun handleCurrentWeatherViewState(resourceState: ResourceState, data: CurrentWeatherView?) {
        when(resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError()
        }
    }

    private fun setupScreenForLoadingState() {
        progress_bar.visibility = if(swipe_refresh_layout.isRefreshing) View.GONE else View.VISIBLE
        swipe_refresh_layout.isEnabled = progress_bar.visibility == View.GONE
        data_view.visibility = View.GONE
        empty_view.visibility = View.GONE
        error_view.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun setupScreenForSuccess(it: CurrentWeatherView?) {
        swipe_refresh_layout.isEnabled = true
        progress_bar.visibility = View.GONE
        error_view.visibility = View.GONE
        empty_view.visibility = View.VISIBLE
        it?.let {
            weather_desc_text_view.text = it.weatherList[0].description.toUpperCase()
            weather_icon_image_view.setImageResource(WeatherIcon.getWeatherId(it.weatherList[0].id, it.weatherList[0].icon))
            temp_text_view.text = "${it.main.temp.roundToInt()}\u00b0"
            min_temp_text_view.text = "\u25bc ${it.main.tempMin.roundToInt()}\u00b0"
            max_temp_text_view.text = "\u25b2 ${it.main.tempMax.roundToInt()}\u00b0"
            wind_text_view.text = "${it.wind.speed} m/s"
            pressure_text_view.text = "${it.main.pressure.roundToInt()} hPa"
            humidity_text_view.text = "${it.main.humidity} %"
            cloud_text_view.text = "${it.clouds.cloudiness} %"
            sunriseTime = it.sys.sunriseTime * 1000
            sunsetTime = it.sys.sunsetTime * 1000
            sunrise_text_view.text = TimeFormat.sunriseTime(sunriseTime!!)
            sunset_text_view.text = TimeFormat.sunsetTime(sunsetTime!!)

            swipe_refresh_layout.isRefreshing = false
            data_view.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
        }
    }

    private fun setupScreenForError() {
        swipe_refresh_layout.isEnabled = false
        swipe_refresh_layout.isRefreshing = false
        progress_bar.visibility = View.GONE
        data_view.visibility = View.GONE
        empty_view.visibility = View.GONE
        error_view.visibility = View.VISIBLE
        error_view.textView.text = getString(R.string.error_text_view)
    }

    private fun getTodayWeatherForecast() {
        weatherForecastViewModel.getWeatherForecast().observe(this,
                Observer<Resource<WeatherForecastView>> {
                    if(view?.parent != null) {
                        it?.let { handleWeatherForecastState(it.status, it.data) }
                    }
                })
    }

    private fun handleWeatherForecastState(resourceState: ResourceState, data: WeatherForecastView?) {
        when(resourceState) {
            ResourceState.LOADING -> setupRecyclerForLoadingState()
            ResourceState.SUCCESS -> setupRecyclerForSuccess(data)
            ResourceState.ERROR -> setupRecyclerForError()
        }
    }

    private fun setupRecyclerForLoadingState() {
        progress_bar_recycler_view.visibility = View.VISIBLE
        rv_weather_forecast.visibility = View.GONE
        empty_recycler_view.visibility = View.GONE
        error_recycler_view.visibility = View.GONE
        button_recycler_view.visibility = View.GONE
        weekly.visibility = View.GONE
    }

    private fun setupRecyclerForSuccess(it: WeatherForecastView?) {
        progress_bar_recycler_view.visibility = View.GONE
        error_recycler_view.visibility = View.GONE
        button_recycler_view.visibility = View.GONE
        it?.let {
            forecastList.clear()
            for(i in 0..6) forecastList.add(it.forecastList[i])
            adapter.notifyDataSetChanged()
            if(forecastList.isEmpty()) {
                empty_recycler_view.visibility = View.VISIBLE
                empty_recycler_view.text = getString(R.string.empty_weekly_alert)
            }
            rv_weather_forecast.visibility = View.VISIBLE
            weekly.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerForError() {
        progress_bar_recycler_view.visibility = View.GONE
        rv_weather_forecast.visibility = View.GONE
        empty_recycler_view.visibility = View.GONE
        weekly.visibility = View.GONE
        if(error_view.visibility == View.VISIBLE) {
            error_recycler_view.visibility = View.GONE
            button_recycler_view.visibility = View.GONE
        } else {
            error_recycler_view.visibility = View.VISIBLE
            button_recycler_view.visibility = View.VISIBLE
        }
    }

    private fun getTimeZone() {
        timeZoneViewModel.getTimeZone().observe(this,
                Observer<TimeZoneView> {
                    TimeFormat.timeZoneId = it?.timeZoneId
                    getCurrentWeather()
                    getTodayWeatherForecast()
                })
    }

}