package com.minimalist.weather.di

import com.minimalist.weather.MinimalistWeatherApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityBuilder::class))
interface ApplicationComponent: AndroidInjector<MinimalistWeatherApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<MinimalistWeatherApplication>() {}

}