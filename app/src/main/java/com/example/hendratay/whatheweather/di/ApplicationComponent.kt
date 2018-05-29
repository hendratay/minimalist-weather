package com.example.hendratay.whatheweather.di

import com.example.hendratay.whatheweather.WhatheWeatherApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityBuilder::class))
interface ApplicationComponent: AndroidInjector<WhatheWeatherApplication> {

/*    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }*/

    //fun inject(app: WhatheWeatherApplication)

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<WhatheWeatherApplication>() {}

}