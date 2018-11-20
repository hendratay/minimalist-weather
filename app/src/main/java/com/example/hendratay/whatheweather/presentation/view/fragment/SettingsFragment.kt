package com.example.hendratay.whatheweather.presentation.view.fragment

import android.annotation.TargetApi
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import com.example.hendratay.whatheweather.R
import android.content.Intent
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Build
import com.example.hendratay.whatheweather.presentation.view.activity.AboutActivity


class SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        onSharedPreferenceChanged(sharedPref, getString(R.string.saved_temp_unit))
        val aboutPreference = findPreference(getString(R.string.about))
        val rateThisAppPreference = findPreference(getString(R.string.rate_this_app))
        aboutPreference.setOnPreferenceClickListener { startActivity(Intent(context, AboutActivity::class.java));true }
        rateThisAppPreference.setOnPreferenceClickListener { openPlayStore();true }
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun openPlayStore() {
        val uri = Uri.parse("market://details?id=${context?.packageName}")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${context?.packageName}")))
        }
    }

}