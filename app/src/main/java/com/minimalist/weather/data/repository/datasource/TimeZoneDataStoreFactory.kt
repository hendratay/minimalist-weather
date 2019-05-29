package com.minimalist.weather.data.repository.datasource

import javax.inject.Inject

class TimeZoneDataStoreFactory @Inject constructor() {

    fun create() = TimeZoneCloudDataStore()

}
