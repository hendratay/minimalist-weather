package com.minimalist.weather.domain.interactor

import io.reactivex.observers.DisposableSingleObserver

open class SingleObserver<T>: DisposableSingleObserver<T>() {

    override fun onSuccess(t: T) {
    }

    override fun onError(e: Throwable) {
    }

}
