package com.minimalist.weather.presentation.view.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeFormat {

    var timeZoneId: String? = null

    fun sunriseTime(sunriseTime: Long): String {
        val sdf = SimpleDateFormat("H : mm", Locale.US)
        sdf.timeZone = if(timeZoneId == null) TimeZone.getTimeZone(TimeZone.getDefault().id) else TimeZone.getTimeZone(timeZoneId)
        return sdf.format(Date(sunriseTime))
    }

    fun sunsetTime(sunsetTime: Long): String {
        val sdf = SimpleDateFormat("H : mm", Locale.US)
        sdf.timeZone = if(timeZoneId == null) TimeZone.getTimeZone(TimeZone.getDefault().id) else TimeZone.getTimeZone(timeZoneId)
        return sdf.format(Date(sunsetTime))
    }

    fun todayForecastGroupTime(todayForecast: Long): Pair<String, String> {
        val sdf = SimpleDateFormat("dd MM yyyy", Locale.US)
        sdf.timeZone = if(timeZoneId == null) TimeZone.getTimeZone(TimeZone.getDefault().id) else TimeZone.getTimeZone(timeZoneId)
        val dateTime = sdf.format(Date(todayForecast))
        val today = sdf.format(Calendar.getInstance().time)
        return Pair(dateTime, today)
    }

    fun todayForecastTime(todayForecastTime: Long): String {
        val sdf = SimpleDateFormat("K a", Locale.US)
        sdf.timeZone = if(timeZoneId == null) TimeZone.getTimeZone(TimeZone.getDefault().id) else TimeZone.getTimeZone(timeZoneId)
        return sdf.format(Date(todayForecastTime))
    }

    fun forecastGroupTime(forecast: Long): String {
        val sdf = SimpleDateFormat("EEE, dd MMM y", Locale.US)
        sdf.timeZone = if(timeZoneId == null) TimeZone.getTimeZone(TimeZone.getDefault().id) else TimeZone.getTimeZone(timeZoneId)
        return sdf.format(Date(forecast))
    }

    fun forecastTime(forecastTime: Long): String {
        val sdf = SimpleDateFormat("K a", Locale.US)
        sdf.timeZone = if(timeZoneId == null) TimeZone.getTimeZone(TimeZone.getDefault().id) else TimeZone.getTimeZone(timeZoneId)
        return sdf.format(Date(forecastTime))
    }

}