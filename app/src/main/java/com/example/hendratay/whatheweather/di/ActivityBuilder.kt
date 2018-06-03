package com.example.hendratay.whatheweather.di

import com.example.hendratay.whatheweather.presentation.view.activity.MainActivity
import com.example.hendratay.whatheweather.presentation.view.activity.MainActivityModule
import com.example.hendratay.whatheweather.presentation.view.fragment.TodayFragment
import com.example.hendratay.whatheweather.presentation.view.fragment.TodayFragmentModule
import com.example.hendratay.whatheweather.presentation.view.fragment.WeeklyFragment
import com.example.hendratay.whatheweather.presentation.view.fragment.WeeklyFragmentModule
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