package com.example.hendratay.whatheweather.presentation.view.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.*
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.data.Resource
import com.example.hendratay.whatheweather.presentation.data.ResourceState
import com.example.hendratay.whatheweather.presentation.model.CurrentWeatherView
import com.example.hendratay.whatheweather.presentation.view.fragment.SettingsFragment
import com.example.hendratay.whatheweather.presentation.view.fragment.TodayFragment
import com.example.hendratay.whatheweather.presentation.view.utils.Location
import com.example.hendratay.whatheweather.presentation.view.utils.Permission
import com.example.hendratay.whatheweather.presentation.view.utils.Temperature
import com.example.hendratay.whatheweather.presentation.viewmodel.*
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.tasks.Task
import com.google.maps.android.SphericalUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

const val PLACE_PICKER_REQUEST_CODE = 1
const val REQUEST_ACCESS_FINE_LOCATION = 111
const val REQUEST_CHECK_SETTINGS = 222
class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.simpleName
    }

    @Inject lateinit var currentWeatherViewModelFactory: CurrentWeatherViewModelFactory
    @Inject lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory
    @Inject lateinit var timeZoneViewModelFactory: TimeZoneViewModelFactory

    private lateinit var menu: Menu
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel
    private lateinit var timeZoneViewModel: TimeZoneViewModel
    private var savedLatitude: Double? = null
    private var savedLongitude: Double? = null
    private var savedUnits: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentWeatherViewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory)[CurrentWeatherViewModel::class.java]
        weatherForecastViewModel = ViewModelProviders.of(this, weatherForecastViewModelFactory)[WeatherForecastViewModel::class.java]
        timeZoneViewModel = ViewModelProviders.of(this, timeZoneViewModelFactory)[TimeZoneViewModel::class.java]

        if(connectivityStatus()) {
            createLocationRequest()
            // initialize location callback first for pass into requestLocationUpdates at `startLocationUpdates`
            receiveLocationUpdates()
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            startLocationUpdates()
        } else {
            currentWeatherViewModel.setLocation(null, null, savedUnits)
            weatherForecastViewModel.setLocation(null, null, savedUnits)
        }

        setupToolbar()
        loadFragment(TodayFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            when(supportFragmentManager.findFragmentById(R.id.fragment_container)) {
                is SettingsFragment -> {
                    Handler().postDelayed({
                        loadFragment(TodayFragment())
                        showToolbar()
                        reloadTemperature()
                    }, 150)
                }
                else -> {
                    Handler().postDelayed({
                        loadFragment(SettingsFragment())
                        hideToolbar()
                    }, 150)
                }
            }
            true
        }
        R.id.gps -> {
            placePickerIntent()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            PLACE_PICKER_REQUEST_CODE -> {
                when(resultCode) {
                    Activity.RESULT_OK -> {
                        val place: Place = PlacePicker.getPlace(this, data)
                        getSavedUnits()
                        currentWeatherViewModel.setLocation(place.latLng.latitude, place.latLng.longitude, savedUnits)
                        weatherForecastViewModel.setLocation(place.latLng.latitude, place.latLng.longitude, savedUnits)
                        weatherForecastViewModel.getWeatherForecast()
                        timeZoneViewModel.setQuery(place.latLng.latitude, place.latLng.longitude)
                        saveLocation(place.latLng.latitude,place.latLng.longitude)
                    }
                    Activity.RESULT_CANCELED -> {
                    }
                }
            }
            REQUEST_CHECK_SETTINGS -> {
                when(resultCode) {
                    Activity.RESULT_OK -> startLocationUpdates()
                    Activity.RESULT_CANCELED -> {
                        getSavedLocation()
                        getSavedUnits()
                        if(savedLatitude != null && savedLongitude != null) {
                            Toast.makeText(this, R.string.notice_use_last_location, Toast.LENGTH_SHORT).show()
                        }
                        currentWeatherViewModel.setLocation(savedLatitude, savedLongitude, savedUnits)
                        weatherForecastViewModel.setLocation(savedLatitude, savedLongitude, savedUnits)
                        timeZoneViewModel.setQuery(savedLatitude, savedLongitude)
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
                    getSavedLocation()
                    getSavedUnits()
                    if(savedLatitude != null && savedLongitude != null) {
                        Toast.makeText(this, R.string.notice_use_last_location, Toast.LENGTH_SHORT).show()
                    }
                    currentWeatherViewModel.setLocation(savedLatitude, savedLongitude, savedUnits)
                    weatherForecastViewModel.setLocation(savedLatitude, savedLongitude, savedUnits)
                    timeZoneViewModel.setQuery(savedLatitude, savedLongitude)
                }
            }
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate(supportFragmentManager.findFragmentById(R.id.fragment_container)::class.simpleName,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)
            if(supportFragmentManager.findFragmentById(R.id.fragment_container)::class.simpleName != "SettingsFragment") {
                showToolbar()
                reloadTemperature()
            }
        } else {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        currentWeatherViewModel.getCurrentWeather().observe(this,
                Observer<Resource<CurrentWeatherView>> {
                    it?.let { handleCurrentWeatherViewState(it.status, it.data) }
                })
    }

    private fun handleCurrentWeatherViewState(status: ResourceState, data: CurrentWeatherView?) {
        when(status) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError()
        }
    }

    private fun setupScreenForLoadingState() {
        toolbar.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: CurrentWeatherView?) {
        toolbar.visibility = View.VISIBLE
        city_name_text_view.text = if(data?.cityName == "") getString(R.string.placeholder_address) else data?.cityName
    }

    private fun setupScreenForError() {
        toolbar.visibility = View.GONE
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        // remove App Title (Whathe Weather)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_settings_black_24dp)
    }

    private fun hideToolbar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = resources.getString(R.string.settings)
        city_name_text_view.visibility = View.GONE
        menu.findItem(R.id.gps).isVisible = false
    }

    private fun showToolbar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_settings_black_24dp)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        city_name_text_view.visibility = View.VISIBLE
        menu.findItem(R.id.gps).isVisible = true
    }

    fun placePickerIntent() {
        getSavedLocation()
        val latLng = LatLng(savedLatitude ?: 0.0, savedLongitude ?: 0.0)
        val distance = 200.0
        val heading = arrayOf(0, 90, 180, 270)
        val latLngBuilder = LatLngBounds.Builder()
        lateinit var point: LatLng
        for(i in heading.indices) {
            point = SphericalUtil.computeOffset(latLng, distance, heading[i].toDouble())
            latLngBuilder.include(point)
        }
        val builder = PlacePicker.IntentBuilder().setLatLngBounds(latLngBuilder.build())
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST_CODE)
    }

    fun loadFragment(fragment: Fragment) {
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
            if(Permission.getPermissionSharedPref(this)) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
                Permission.savePermissionSharedPref(this, false)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
                Snackbar.make(coordinator_layout, R.string.notice_asking_enable_location_permission, Snackbar.LENGTH_LONG)
                        .setAction(R.string.notice_enable_location_permission) {
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
                    Toast.makeText(this, R.string.notice_error_enable_location, Toast.LENGTH_SHORT).show()
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
            override fun onLocationResult(p0: LocationResult?) {
                p0 ?: return
                for(location in p0.locations) {
                    getSavedUnits()
                    currentWeatherViewModel.setLocation(location.latitude, location.longitude, savedUnits)
                    weatherForecastViewModel.setLocation(location.latitude, location.longitude, savedUnits)
                    timeZoneViewModel.setQuery(location.latitude, location.longitude)
                    saveLocation(location.latitude,location.longitude)
                }
                stopLocationUpdates()
            }
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun connectivityStatus(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting ?: false
    }

    private fun saveLocation(lat: Double, lng: Double) {
        Location.saveLatSharedPref(this, java.lang.Double.doubleToRawLongBits(lat))
        Location.saveLngSharedPref(this, java.lang.Double.doubleToRawLongBits(lng))
    }

    private fun getSavedLocation() {
        savedLatitude = java.lang.Double.longBitsToDouble(Location.getLatSharedPref(this))
        savedLongitude = java.lang.Double.longBitsToDouble(Location.getLngSharedPref(this))
        if(savedLatitude == 0.0) savedLatitude = null
        if(savedLongitude == 0.0) savedLongitude = null
    }

    private fun getSavedUnits() {
        savedUnits = Temperature.getTempSharedPrefs(this)
    }

    /**
     * Check temperature units, if saved preferences is changed then reload temperature data
     */
    private fun reloadTemperature() {
        if(Temperature.getTempSharedPrefs(this) != savedUnits) {
            getSavedUnits()
            getSavedLocation()
            currentWeatherViewModel.setLocation(savedLatitude, savedLongitude, savedUnits)
            weatherForecastViewModel.setLocation(savedLatitude, savedLongitude, savedUnits)
        }
    }

}
