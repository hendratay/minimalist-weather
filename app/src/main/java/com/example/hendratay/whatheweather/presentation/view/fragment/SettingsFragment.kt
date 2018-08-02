package com.example.hendratay.whatheweather.presentation.view.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.view.activity.MainActivity

class SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        onSharedPreferenceChanged(sharedPref, getString(R.string.saved_temp_unit))
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val preference = findPreference(key)
        if (preference is ListPreference) {
            val prefIndex = preference.findIndexOfValue(sharedPreferences?.getString(key, getString(R.string.celsius)))
            preference.setSummary(preference.entries[prefIndex])
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

}