package com.example.hendratay.whatheweather.presentation.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.data.Resource
import com.example.hendratay.whatheweather.presentation.data.ResourceState
import com.example.hendratay.whatheweather.presentation.model.*
import com.example.hendratay.whatheweather.presentation.view.activity.MainActivity
import com.example.hendratay.whatheweather.presentation.view.adapter.ForecastWeeklyAdapter
import com.example.hendratay.whatheweather.presentation.view.utils.CirclePagerIndicatorDecoration
import com.example.hendratay.whatheweather.presentation.view.utils.TimeFormat
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_weekly.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class WeeklyFragment: Fragment() {

    companion object {
        private val TAG = WeeklyFragment::class.simpleName
    }

    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel

    private lateinit var adapter: ForecastWeeklyAdapter
    private var weekList: MutableList<Map<String, List<ForecastView>>> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        setupEmptyErrorButtonClick()

        weatherForecastViewModel = ViewModelProviders.of(activity as MainActivity, weatherForecastViewModelFactory).get(WeatherForecastViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        getWeeklyForecast()
    }

    private fun setupRecyclerView() {
        adapter = ForecastWeeklyAdapter(weekList) { it -> weeklyForecastClick(it) }
        PagerSnapHelper().attachToRecyclerView(rv_forecast_weekly)
        rv_forecast_weekly.layoutManager = LinearLayoutManager(activity as MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_forecast_weekly.adapter = adapter
        rv_forecast_weekly.setHasFixedSize(true)
        rv_forecast_weekly.addItemDecoration(CirclePagerIndicatorDecoration())
    }

    private fun setupEmptyErrorButtonClick() {
        button_weekly.setOnClickListener {
            weatherForecastViewModel.fetchWeatherForecast()
        }
    }

    private fun getWeeklyForecast() {
        weatherForecastViewModel.getWeatherForecast().observe(activity as MainActivity,
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
        progress_bar_weekly.visibility = View.VISIBLE
        rv_forecast_weekly.visibility = View.GONE
        error_cloud.visibility = View.GONE
        empty_weekly.visibility = View.GONE
        error_weekly.visibility = View.GONE
        button_weekly.visibility = View.GONE
    }

    private fun setupRecyclerForSuccess(it: WeatherForecastView?) {
        progress_bar_weekly.visibility = View.GONE
        error_cloud.visibility = View.GONE
        error_weekly.visibility = View.GONE
        button_weekly.visibility = View.GONE
        it?.let {
            weekList.clear()
            val groupedHashMap: Map<String, MutableList<ForecastView>> = groupDataIntoHashMap(it.forecastList.toMutableList())
            for(forecastDate: String in groupedHashMap.keys) {
                weekList.add(mapOf(forecastDate to (groupedHashMap[forecastDate] ?: emptyList<ForecastView>())))
            }
            adapter.notifyDataSetChanged()
            if(weekList.isEmpty()) {
                error_cloud.visibility = View.VISIBLE
                empty_weekly.visibility = View.VISIBLE
                button_weekly.visibility = View.VISIBLE
            }
            rv_forecast_weekly.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerForError() {
        progress_bar_weekly.visibility = View.GONE
        rv_forecast_weekly.visibility = View.GONE
        error_cloud.visibility = View.VISIBLE
        empty_weekly.visibility = View.GONE
        error_weekly.visibility = View.VISIBLE
        button_weekly.visibility = View.VISIBLE
    }

    private fun groupDataIntoHashMap(forecastList: MutableList<ForecastView>): Map<String, MutableList<ForecastView>> {
        val groupedHashMap: HashMap<String, MutableList<ForecastView>> = HashMap()
        for(forecastView: ForecastView in forecastList) {
            val date = TimeFormat.forecastGroupTime(forecastView.dateTime * 1000)
            if(groupedHashMap.containsKey(date)) {
                groupedHashMap[date]?.add(forecastView)
            } else {
                val list: MutableList<ForecastView> = ArrayList()
                list.add(forecastView)
                groupedHashMap[date] = list
            }
        }
        return groupedHashMap.toList().sortedBy { (key, _) -> Date(key) }.toMap()
    }

    private fun weeklyForecastClick(forecastView: ForecastView) {
        val bundle = Bundle()
        bundle.putSerializable("FORECAST_VIEW", forecastView)

        val dialog = WeatherDetailFragment()
        dialog.arguments = bundle
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

}
