package com.example.hendratay.whatheweather.data.repository.datasource

import javax.inject.Inject

class WeatherDataStoreFactory @Inject constructor() {

    fun create() = WeatherCloudDataStore()

}