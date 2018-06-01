package com.example.hendratay.whatheweather.presentation.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.data.Resource
import com.example.hendratay.whatheweather.presentation.data.ResourceState
import com.example.hendratay.whatheweather.presentation.model.ForecastView
import com.example.hendratay.whatheweather.presentation.model.WeatherForecastView
import com.example.hendratay.whatheweather.presentation.view.activity.MainActivity
import com.example.hendratay.whatheweather.presentation.view.adapter.ForecastWeeklyAdapter
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_weekly.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeeklyFragment: Fragment() {

    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel

    private lateinit var adapter: ForecastWeeklyAdapter
    private var forecastList: MutableList<ForecastView> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()

        weatherForecastViewModel = ViewModelProviders.of(activity as MainActivity, weatherForecastViewModelFactory).get(WeatherForecastViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        getWeeklyForecast()
    }

    private fun setupRecyclerView() {
        rv_forecast_weekly.layoutManager = GridLayoutManager(activity as MainActivity, 4)
        adapter = ForecastWeeklyAdapter(forecastList)
        rv_forecast_weekly.adapter = adapter
    }

    private fun getWeeklyForecast() {
        weatherForecastViewModel.getWeatherForecast().observe(activity as MainActivity,
                Observer<Resource<WeatherForecastView>> {
                    it?.let { handleWeatherForecastState(it.status, it.data, it.message) }
                })
    }

    private fun handleWeatherForecastState(resoureState: ResourceState, data: WeatherForecastView?, message: String?) {
        when(resoureState) {
            ResourceState.LOADING -> setupRecyclerForLoadingState()
            ResourceState.SUCCESS -> setupRecyclerForSuccess(data)
            ResourceState.ERROR -> setupRecylerForError(message)
        }
    }

    private fun setupRecyclerForLoadingState() {
    }

    private fun setupRecyclerForSuccess(it: WeatherForecastView?) {
        it?.let {
            forecastList.clear()
            it.forecastList.forEach {
                val sdf = SimpleDateFormat("dd MM yyyy")
                val dateTime = sdf.format(Date(it.dateTime * 1000))
                val currentDateTime = sdf.format(Calendar.getInstance().time)
                forecastList.add(it)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupRecylerForError(message: String?) {
    }


}

