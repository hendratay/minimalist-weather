package com.example.hendratay.whatheweather.data.repository.datasource

import javax.inject.Inject

class TimeZoneDataStoreFactory @Inject constructor() {

    fun create() = TimeZoneCloudDataStore()

}
