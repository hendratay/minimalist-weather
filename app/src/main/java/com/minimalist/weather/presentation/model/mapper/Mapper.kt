package com.minimalist.weather.presentation.model.mapper

interface Mapper<V, D> {

    fun mapToView(type: D): V

}