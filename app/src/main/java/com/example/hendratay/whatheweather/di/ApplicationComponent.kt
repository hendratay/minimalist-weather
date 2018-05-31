package com.example.hendratay.whatheweather.di

import com.example.hendratay.whatheweather.WhatheWeatherApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityBuilder::class))
interface ApplicationComponent: AndroidInjector<WhatheWeatherApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<WhatheWeatherApplication>() {}

}