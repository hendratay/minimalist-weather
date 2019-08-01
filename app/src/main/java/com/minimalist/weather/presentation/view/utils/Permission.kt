package com.minimalist.weather.presentation.view.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.minimalist.weather.R

class Permission {

    companion object {
        private fun getSharedPrefs(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun savePermissionSharedPref(context: Context, check: Boolean) {
            val editor =  getSharedPrefs(context).edit()
            editor.putBoolean(context.getString(R.string.saved_permission), check)
            editor.apply()
        }

        fun getPermissionSharedPref(context: Context): Boolean {
            val prefs = getSharedPrefs(context)
            return prefs.getBoolean(context.getString(R.string.saved_permission), true)
        }
    }

}