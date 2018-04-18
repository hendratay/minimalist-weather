package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.Main
import com.example.hendratay.whatheweather.presentation.model.MainView
import javax.inject.Inject

class MainViewMapper @Inject constructor(): Mapper<MainView, Main> {

    override fun mapToView(type: Main): MainView {
        return MainView(type.temp,
                type.tempMin,
                type.tempMax,
                type.pressure,
                type.seaLevel,
                type.groundLevel,
                type.humidity)
    }

}