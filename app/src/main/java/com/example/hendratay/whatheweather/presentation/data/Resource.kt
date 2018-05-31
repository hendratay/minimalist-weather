package com.example.hendratay.whatheweather.presentation.data

class Resource<T> constructor(val status: ResourceState, val data: T?, val message: String?) {

    /*
    fun <T> loading(): Resource<T> {
        return Resource(ResourceState.LOADING, null, null)
    }

    fun <T> success(data: T): Resource<T> {
        return Resource(ResourceState.SUCCESS, data, null)
    }

    fun <T> error(message: String): Resource<T> {
        return Resource(ResourceState.ERROR, null, message)
    }
    */

}