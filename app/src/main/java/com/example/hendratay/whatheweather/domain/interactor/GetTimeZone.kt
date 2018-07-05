package com.example.hendratay.whatheweather.domain.interactor

import com.example.hendratay.whatheweather.domain.model.TimeZone
import com.example.hendratay.whatheweather.domain.repository.TimeZoneRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTimeZone @Inject constructor(private val timeZoneRepository: TimeZoneRepository): SingleUseCase<TimeZone, GetTimeZone.Params>() {

    override fun buildUseCaseObservable(params: Params?): Single<TimeZone> {
        return timeZoneRepository.getTimeZone(params!!.location, params!!.timestamp)
    }

    class Params(val location: String, val timestamp: Long) {

        companion object {
            fun forTimeZone(location: String, timestamp: Long): Params {
                return Params(location, timestamp)
            }
        }

    }

}
