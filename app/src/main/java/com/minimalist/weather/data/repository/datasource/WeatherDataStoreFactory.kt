package com.minimalist.weather.data.repository.datasource

import javax.inject.Inject

class WeatherDataStoreFactory @Inject constructor() {

    fun create() = WeatherCloudDataStore()

}