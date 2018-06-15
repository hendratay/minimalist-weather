package com.example.hendratay.whatheweather.presentation.view.utils

import com.example.hendratay.whatheweather.R

object WeatherIcon {
    fun getWeatherId(id: Int, icon: String)= when (id){
/*        200, 201, 202, 230, 231, 232 -> { R.drawable.wi_storm_showers }
        210, 211, 212, 221 -> { R.drawable.wi_thunderstorm }
        300, 301, 302, 310, 311, 312, 313, 314, 321 -> { R.drawable.wi_showers}
        500, 501, 502, 503, 504 -> { R.drawable.wi_rain }
        511 -> { R.drawable.wi_rain_mix }
        520, 521, 522, 531 -> { R.drawable.wi_showers }
        600, 601, 602, 611, 612, 615, 616, 620, 621, 622 -> { R.drawable.wi_snow }
        701, 721, 741 -> { R.drawable.wi_fog }
        711 -> { R.drawable.wi_smoke }
        731, 751, 761 -> { R.drawable.wi_dust }
        762 -> { R.drawable.wi_volcano }
        771, 781 -> { R.drawable.wi_tornado }
        800 -> {
            if (icon.takeLast(1) == "d") {
                R.drawable.wi_sunny
            } else {
                R.drawable.wi_night_clear
            }
        }*/
        else -> { R.drawable.wi_sunny }
    }
}
