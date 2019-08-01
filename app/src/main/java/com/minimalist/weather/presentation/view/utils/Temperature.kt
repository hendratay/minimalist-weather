package com.minimalist.weather.presentation.view.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.minimalist.weather.R

class Temperature {

    companion object {
        private fun getSharedPrefs(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun getTempSharedPrefs(context: Context): String {
            val prefs = getSharedPrefs(context)
            return prefs.getString(context.getString(R.string.saved_temp_unit), context.getString(R.string.celsius)) ?: context.getString(R.string.celsius)
        }
    }

}