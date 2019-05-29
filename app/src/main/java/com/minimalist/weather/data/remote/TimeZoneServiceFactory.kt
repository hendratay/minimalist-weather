package com.minimalist.weather.data.remote

import com.minimalist.weather.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object TimeZoneServiceFactory {

    fun makeService(): TimeZoneService {
        return makeTimeZoneService(makeOkkHttpClient(TimeZoneInterceptor()), makeGson())
    }

    private fun makeTimeZoneService(okHttpClient: OkHttpClient, gson: Gson): TimeZoneService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/timezone/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(TimeZoneService::class.java)
    }

    private fun makeOkkHttpClient(timeZoneInterceptor: TimeZoneInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(timeZoneInterceptor)
                .build()
    }

    private fun makeGson() = GsonBuilder().create()

}

class TimeZoneInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request().url().newBuilder()
                .addQueryParameter("key", BuildConfig.GOOGLE_MAPS_API_KEY)
                .build()
        return chain.proceed(chain.request().newBuilder().addHeader("Accept", "application/json").url(url).build())
    }

}
