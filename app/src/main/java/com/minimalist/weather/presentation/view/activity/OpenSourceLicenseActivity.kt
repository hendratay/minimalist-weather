package com.minimalist.weather.presentation.view.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mikepenz.aboutlibraries.LibsBuilder
import com.minimalist.weather.R
import kotlinx.android.synthetic.main.activity_open_source_license.*

class OpenSourceLicenseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source_license)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setColorFilter(resources.getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP)
        setupAboutLibraries()
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupAboutLibraries() {
        val fragment = LibsBuilder().supportFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

}