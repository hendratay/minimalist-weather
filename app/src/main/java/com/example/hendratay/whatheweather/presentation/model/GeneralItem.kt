package com.example.hendratay.whatheweather.presentation.model

class GeneralItem(val forecastView: ForecastView): ListItem() {

    override fun getType(): Int {
       return TYPE_GENERAL
    }

}
