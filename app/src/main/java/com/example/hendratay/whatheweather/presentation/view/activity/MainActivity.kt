package com.example.hendratay.whatheweather.presentation.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
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
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModel
import com.example.hendratay.whatheweather.presentation.viewmodel.WeatherForecastViewModelFactory
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.tasks.Task
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*
import javax.inject.Inject

const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
const val REQUEST_ACCESS_FINE_LOCATION = 111
const val REQUEST_CHECK_SETTINGS = 222
class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.simpleName
    }

    @Inject lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel
    // Todo: follow display a location address guide
    private lateinit var geocoder: Geocoder
    private var address: List<Address> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createLocationRequest()
        // initialize location callback first for pass into requestlocationupdates at `startLocationUpdates`
        receiveLocationUpdates()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale.getDefault())
        startLocationUpdates()

        currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory)[CurrentWeatherViewModel::class.java]
        weatherForecastViewModel = ViewModelProviders.of(this, weatherForecastViewModelFactory)[WeatherForecastViewModel::class.java]

        setupToolbar()
        setupWeeklyButton()
        loadFragment(TodayFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        R.id.search -> {
            autoCompleteIntent()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            PLACE_AUTOCOMPLETE_REQUEST_CODE -> {
                when(resultCode) {
                    Activity.RESULT_OK -> {
                        val place: Place = PlaceAutocomplete.getPlace(this, data)
                        currentWeatherViewModel.setLatLng(place.latLng.latitude, place.latLng.longitude)
                        weatherForecastViewModel.setlatLng(place.latLng.latitude, place.latLng.longitude)
                        // Todo: Follow display a location address guide at android developer
                        try {
                            address = geocoder.getFromLocation(place.latLng.latitude, place.latLng.longitude, 1)
                            city_name_text_view.text = "${address[0].thoroughfare} \n".capitalize() +
                                    "${address[0].locality}, ${address[0].countryName}".capitalize()
                        } catch (e: IOException) {
                            city_name_text_view.text = "${place.address}"
                        }
                    }
                    Activity.RESULT_CANCELED -> {
                    }
                    PlaceAutocomplete.RESULT_ERROR -> {
                        val status: Status = PlaceAutocomplete.getStatus(this, data)
                        Log.i(TAG, status.statusMessage)
                    }
                }
            }
            REQUEST_CHECK_SETTINGS -> {
                when(resultCode) {
                    Activity.RESULT_OK -> startLocationUpdates()
                    Activity.RESULT_CANCELED -> {
                        currentWeatherViewModel.setLatLng(0.0, 0.0)
                        weatherForecastViewModel.setlatLng(0.0, 0.0)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates()
                } else {
                    currentWeatherViewModel.setLatLng(0.0, 0.0)
                    weatherForecastViewModel.setlatLng(0.0, 0.0)
                }
            }
        }

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

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        // remove App Title (Whathe Weather)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupWeeklyButton() {
        weekly.setOnClickListener {
            loadFragment(WeeklyFragment())
            weekly.visibility = View.GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun autoCompleteIntent() {
        try {
            val intent: Intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            Log.e(TAG, e.message)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.e(TAG, e.message)
        }
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

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
            Snackbar.make(coordinator_layout, "Please Enable Location Permission", Snackbar.LENGTH_LONG)
                    .setAction("Enable") {
                        val intent = Intent()
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = uri
                        }
                        startActivity(intent)
                    }
                    .show()
        }
    }

    fun createLocationRequest() {
        locationRequest = LocationRequest().apply {
            interval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener {
            if (it is ResolvableApiException){
                try {
                    it.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.e(TAG, "Error at LocationSettingsResponse.addOnFailureListener")
                }
            }
        }
    }

    fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            requestLocationPermission()
        }
    }

    private fun receiveLocationUpdates() {
        locationCallback = object : LocationCallback() {
            @SuppressLint("SetTextI18n")
            override fun onLocationResult(p0: LocationResult?) {
                p0 ?: return
                for(location in p0.locations) {
                    currentWeatherViewModel.setLatLng(location.latitude, location.longitude)
                    weatherForecastViewModel.setlatLng(location.latitude, location.longitude)
                    // Todo: Follow display a location address guide at android developer
                    address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                }
                city_name_text_view.text = "${address[0].thoroughfare} \n".capitalize() +
                        "${address[0].locality}, ${address[0].countryName}".capitalize()
                stopLocationUpdates()
            }
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}
