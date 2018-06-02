package com.example.hendratay.whatheweather.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hendratay.whatheweather.R
import com.example.hendratay.whatheweather.presentation.view.activity.MainActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener

class LocationFragment: Fragment() {

    private lateinit var autoCompleteFragment: PlaceAutocompleteFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        autoCompleteFragment = (activity as MainActivity).fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place?) {
                Log.d("Location", p0?.name.toString())
            }

            override fun onError(p0: Status?) {
                Log.d("Location", p0?.status.toString())
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val fragment = activity?.fragmentManager?.findFragmentById(R.id.place_autocomplete_fragment)
        activity?.fragmentManager
                ?.beginTransaction()
                ?.remove(fragment)
                ?.commit()
    }

}
