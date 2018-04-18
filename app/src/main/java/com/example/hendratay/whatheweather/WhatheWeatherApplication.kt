package com.example.hendratay.whatheweather

import android.app.Application
import com.example.hendratay.whatheweather.di.ApplicationComponent
import com.example.hendratay.whatheweather.di.DaggerApplicationComponent

class WhatheWeatherApplication: Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.create()
    }

}