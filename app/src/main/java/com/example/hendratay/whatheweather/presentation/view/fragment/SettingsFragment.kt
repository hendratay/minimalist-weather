package com.example.hendratay.whatheweather.presentation.view.fragment

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.example.hendratay.whatheweather.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

}