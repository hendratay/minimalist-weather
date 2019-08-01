package com.minimalist.weather.presentation.view.fragment

import android.content.SharedPreferences
import android.os.Bundle
import com.minimalist.weather.R
import android.content.Intent
import android.net.Uri
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.minimalist.weather.presentation.view.activity.AboutActivity

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        onSharedPreferenceChanged(sharedPref, getString(R.string.saved_temp_unit))
        val aboutPreference = findPreference(getString(R.string.about))
        val sendFeedbackPreference = findPreference(getString(R.string.send_feedback))
        val rateThisAppPreference = findPreference(getString(R.string.rate_this_app))
        aboutPreference.onPreferenceClickListener =  Preference.OnPreferenceClickListener { startActivity(Intent(context, AboutActivity::class.java)); true }
        sendFeedbackPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener { openGithubIssues(); true }
        rateThisAppPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener { openPlayStore(); true }
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

    private fun openGithubIssues() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hendratay/minimalist-weather/issues"))
        startActivity(browserIntent)
    }

    private fun openPlayStore() {
        startActivity(Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=${context?.packageName}")))
    }

}