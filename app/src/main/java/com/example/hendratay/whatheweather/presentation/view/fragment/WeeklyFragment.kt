package com.example.hendratay.whatheweather.presentation.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
import com.example.hendratay.whatheweather.presentation.model.*
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

    companion object {
        private val TAG = WeeklyFragment::class.simpleName
    }

    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel

    private lateinit var adapter: ForecastWeeklyAdapter
    private var consolidatedList: MutableList<ListItem> = ArrayList()

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
        rv_forecast_weekly.layoutManager = LinearLayoutManager(activity as MainActivity)
        adapter = ForecastWeeklyAdapter(consolidatedList)
        rv_forecast_weekly.adapter = adapter
    }

    private fun getWeeklyForecast() {
        weatherForecastViewModel.getWeatherForecast().observe(activity as MainActivity,
                Observer<Resource<WeatherForecastView>> {
                    if(view?.parent != null) {
                        it?.let { handleWeatherForecastState(it.status, it.data, it.message) }
                    }
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
        progress_bar_weekly.visibility = View.VISIBLE
        rv_forecast_weekly.visibility = View.GONE
        empty_weekly.visibility = View.GONE
        error_weekly.visibility = View.GONE
        button_weekly.visibility = View.GONE
    }

    private fun setupRecyclerForSuccess(it: WeatherForecastView?) {
        progress_bar_weekly.visibility = View.GONE
        error_weekly.visibility = View.GONE
        button_weekly.visibility = View.GONE
        it?.let {
            consolidatedList.clear()
            val groupedHashMap: Map<String, MutableList<ForecastView>> = groupDataIntoHashMap(it.forecastList.toMutableList())
            for(forecastDate: String in groupedHashMap.keys) {
                val dateItem = DateItem(forecastDate)
                consolidatedList.add(dateItem)
                for (forecast: ForecastView in groupedHashMap[forecastDate]!!) {
                    val generalItem = GeneralItem(forecast)
                    consolidatedList.add(generalItem)
                }
            }
            adapter.notifyDataSetChanged()
            if(consolidatedList.isEmpty()) empty_weekly.visibility = View.VISIBLE
            rv_forecast_weekly.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerForError(message: String?) {
        progress_bar_weekly.visibility = View.GONE
        rv_forecast_weekly.visibility = View.GONE
        empty_weekly.visibility = View.GONE
        error_weekly.text = message
        error_weekly.visibility = View.VISIBLE
        button_weekly.visibility = View.VISIBLE
    }

    private fun groupDataIntoHashMap(forecastList: MutableList<ForecastView>): Map<String, MutableList<ForecastView>> {
        val groupedHashMap: HashMap<String, MutableList<ForecastView>> = HashMap()
        for(forecastView: ForecastView in forecastList) {
            val sdf = SimpleDateFormat("dd MMM y, EEE", Locale.getDefault())
            val date = sdf.format(Date(forecastView.dateTime * 1000))
            if(groupedHashMap.containsKey(date)) {
                groupedHashMap[date]?.add(forecastView)
            } else {
                val list: MutableList<ForecastView> = ArrayList()
                list.add(forecastView)
                groupedHashMap[date] = list
            }
        }
        return groupedHashMap.toList().sortedBy { (key, _) -> key }.toMap()
    }

}

