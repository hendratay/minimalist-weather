package com.example.hendratay.whatheweather.presentation.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.view.fragment.LocationFragment
import com.example.hendratay.whatheweather.presentation.view.fragment.TodayFragment
import com.example.hendratay.whatheweather.presentation.view.fragment.WeeklyFragment
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        loadFragment(TodayFragment())

        bottom_navigation_view.setOnNavigationItemSelectedListener {
            lateinit var fragment: Fragment
            when(it.itemId) {
                R.id.today -> {
                    fragment = TodayFragment()
                    supportActionBar?.show()
                }
                R.id.weekly -> {
                    fragment = WeeklyFragment()
                    supportActionBar?.show()
                }
                R.id.location -> {
                    fragment = LocationFragment()
                    supportActionBar?.hide()
                }
            }
            loadFragment(fragment)
            true
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
                    bottom_navigation_view.menu.getItem(0).setChecked(true)
                    supportActionBar?.show()
                }
                is WeeklyFragment -> {
                    bottom_navigation_view.menu.getItem(1).setChecked(true)
                    supportActionBar?.show()
                }
                is LocationFragment -> {
                    bottom_navigation_view.menu.getItem(2).setChecked(true)
                    supportActionBar?.hide()
                }
            }
        } else {
            finish()
        }
    }

}
