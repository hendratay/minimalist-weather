package com.example.hendratay.whatheweather.presentation.view.utils

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.preference.PreferenceManager
import com.example.hendratay.whatheweather.R

class Temperature {

    companion object {
        private fun getSharedPrefs(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun getTempSharedPrefs(context: Context): String {
            val prefs = getSharedPrefs(context)
            return prefs.getString(context.getString(R.string.saved_temp_unit), context.getString(R.string.celsius))
        }
    }

}