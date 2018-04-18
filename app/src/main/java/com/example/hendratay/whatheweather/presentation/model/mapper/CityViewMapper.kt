package com.example.hendratay.whatheweather.presentation.model.mapper

import com.example.hendratay.whatheweather.domain.model.City
import com.example.hendratay.whatheweather.presentation.model.CityView
import javax.inject.Inject

class CityViewMapper @Inject constructor(val coordinateViewMapper: CoordinateViewMapper) : Mapper<CityView, City> {

    override fun mapToView(type: City): CityView {
        return CityView(type.cityId, type.cityName, coordinateViewMapper.mapToView(type.coordinate), type.countryCode)
    }

}