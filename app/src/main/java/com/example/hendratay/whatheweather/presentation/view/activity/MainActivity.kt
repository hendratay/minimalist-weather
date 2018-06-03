package com.example.hendratay.whatheweather.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.view.fragment.TodayFragment
import com.example.hendratay.whatheweather.presentation.view.fragment.WeeklyFragment
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "MainActivity"
const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        loadFragment(TodayFragment())

        weekly.setOnClickListener {
            loadFragment(WeeklyFragment())
            weekly.visibility = View.GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home ->  {
            onBackPressed()
            true
        }
        R.id.search -> {
            autoCompleteIntent()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun autoCompleteIntent() {
        try {
            val intent: Intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            Log.e(TAG, e.message)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.e(TAG, e.message)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            when(resultCode) {
                Activity.RESULT_OK -> {
                    val place: Place = PlaceAutocomplete.getPlace(this, data)
                    Log.d(TAG, place.toString())
                }
                Activity.RESULT_CANCELED -> {

                }
                PlaceAutocomplete.RESULT_ERROR -> {
                    val status: Status = PlaceAutocomplete.getStatus(this, data)
                    Log.i(TAG, status.toString())
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        // remove App Title (Whathe Weather)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun loadFragment(fragment: Fragment) {
        val newFragment: Fragment? = supportFragmentManager.findFragmentByTag(fragment::class.simpleName)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, newFragment ?: fragment, fragment::class.simpleName)
                // the argument is for POP_BACK_STACK_INCLUSIVE
                .addToBackStack(fragment::class.simpleName)
                .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate(supportFragmentManager.findFragmentById(R.id.fragment_container)::class.simpleName,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)
            when(supportFragmentManager.findFragmentById(R.id.fragment_container)) {
                is TodayFragment -> {
                    weekly.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                is WeeklyFragment -> {
                    weekly.visibility = View.GONE
                }
            }
        } else {
            finish()
        }
    }

}
