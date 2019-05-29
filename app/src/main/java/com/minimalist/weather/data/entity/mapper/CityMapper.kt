package com.minimalist.weather.data.entity.mapper

import com.minimalist.weather.data.entity.CityEntity
import com.minimalist.weather.domain.model.City
import javax.inject.Inject

class CityMapper @Inject constructor(val coordinateMapper: CoordinateMapper): Mapper<CityEntity, City> {

    override fun mapFromEntity(type: CityEntity): City {
        return City(type.cityId, type.cityName, coordinateMapper.mapFromEntity(type.coordinate), type.countryCode)
    }

    override fun mapToEntity(type: City): CityEntity {
        return CityEntity(type.cityId, type.cityName, coordinateMapper.mapToEntity(type.coordinate), type.countryCode)
    }

}