package com.example.hendratay.whatheweather.data.entity.mapper

import com.example.hendratay.whatheweather.data.entity.CityEntity
import com.example.hendratay.whatheweather.domain.model.City

class CityMapper(val coordinateMapper: CoordinateMapper): Mapper<CityEntity, City> {

    override fun mapFromEntity(type: CityEntity): City {
        return City(type.cityId, type.cityName, coordinateMapper.mapFromEntity(type.coordinate), type.countryCode)
    }

    override fun mapToEntity(type: City): CityEntity {
        return CityEntity(type.cityId, type.cityName, coordinateMapper.mapToEntity(type.coordinate), type.countryCode)
    }

}