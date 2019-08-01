package com.minimalist.weather.presentation.view.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.minimalist.weather.R

class Location {

    companion object {
        private fun getSharedPrefs(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun saveLatSharedPref(context: Context, lat: Long) {
            val editor =  getSharedPrefs(context).edit()
            editor.putLong(context.getString(R.string.saved_latitude), lat)
            editor.apply()
        }

        fun saveLngSharedPref(context: Context, lng: Long) {
            val editor =  getSharedPrefs(context).edit()
            editor.putLong(context.getString(R.string.saved_longitude), lng)
            editor.apply()
        }

        fun getLatSharedPref(context: Context): Long {
            val prefs = getSharedPrefs(context)
            return prefs.getLong(context.getString(R.string.saved_latitude), 0)
        }

        fun getLngSharedPref(context: Context): Long {
            val prefs = getSharedPrefs(context)
            return prefs.getLong(context.getString(R.string.saved_longitude), 0)
        }
    }

}