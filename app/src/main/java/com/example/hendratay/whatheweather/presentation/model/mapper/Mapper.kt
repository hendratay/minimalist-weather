package com.example.hendratay.whatheweather.presentation.model.mapper

interface Mapper<V, D> {

    fun mapToView(type: D): V

}