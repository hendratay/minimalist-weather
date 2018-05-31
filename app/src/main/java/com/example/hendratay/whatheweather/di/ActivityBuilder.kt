package com.example.hendratay.whatheweather.di

import com.example.hendratay.whatheweather.presentation.view.fragment.TodayFragment
import com.example.hendratay.whatheweather.presentation.view.fragment.TodayFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(TodayFragmentModule::class))
    abstract fun bindTodayFragment(): TodayFragment

}