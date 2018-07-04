package com.example.hendratay.whatheweather.presentation.view.utils

import com.example.hendratay.whatheweather.R

object WeatherIcon {
    fun getWeatherId(id: Int, icon: String)= when (id){
        200, 201, 202, 210, 211, 212, 221, 230, 231, 232 -> R.drawable.wi_thunderstorm
        300, 301, 302, 310, 311, 312, 313, 314, 321 -> R.drawable.wi_drizzle
        500, 501, 502, 503, 504 -> R.drawable.wi_sunny_rain
        520, 521, 522, 531 -> R.drawable.wi_night_rain
        511, 600, 601, 602, 611, 612, 615, 616, 620, 621, 622 -> R.drawable.wi_snowy
        701, 711, 721, 731, 741, 751, 761 -> R.drawable.wi_windy
        // todo: replace with shock icon
//        762, 771, 781 -> R.drawable.
        800 -> if (icon.takeLast(1) == "d") R.drawable.wi_sunny else R.drawable.wi_night
        801 -> if (icon.takeLast(1) == "d") R.drawable.wi_sunny_cloud else R.drawable.wi_night_cloud
        802 -> R.drawable.wi_cloudy
        803, 804 -> if (icon.takeLast(1) == "d") R.drawable.wi_sunny_gloom else R.drawable.wi_night_gloom
        // todo: replace with confused icon
        else -> R.drawable.wi_sunny
    }
}
