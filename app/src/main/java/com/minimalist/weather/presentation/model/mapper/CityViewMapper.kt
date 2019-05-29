package com.minimalist.weather.presentation.model.mapper

import com.minimalist.weather.domain.model.City
import com.minimalist.weather.presentation.model.CityView
import javax.inject.Inject

class CityViewMapper @Inject constructor(val coordinateViewMapper: CoordinateViewMapper) : Mapper<CityView, City> {

    override fun mapToView(type: City): CityView {
        return CityView(type.cityId, type.cityName, coordinateViewMapper.mapToView(type.coordinate), type.countryCode)
    }

}