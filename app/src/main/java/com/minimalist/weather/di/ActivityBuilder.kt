package com.minimalist.weather.di

import com.minimalist.weather.presentation.view.activity.MainActivity
import com.minimalist.weather.presentation.view.activity.MainActivityModule
import com.minimalist.weather.presentation.view.fragment.TodayFragment
import com.minimalist.weather.presentation.view.fragment.TodayFragmentModule
import com.minimalist.weather.presentation.view.fragment.WeeklyFragment
import com.minimalist.weather.presentation.view.fragment.WeeklyFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(TodayFragmentModule::class))
    abstract fun bindTodayFragment(): TodayFragment

    @ContributesAndroidInjector(modules = arrayOf(WeeklyFragmentModule::class))
    abstract fun bindWeeklyFragment(): WeeklyFragment

}