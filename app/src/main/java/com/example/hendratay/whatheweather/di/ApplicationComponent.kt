package com.example.hendratay.whatheweather.di

import com.example.hendratay.whatheweather.WhatheWeatherApplication
import com.example.hendratay.whatheweather.presentation.view.activity.MainActivity
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(app: MainActivity)

}