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
import java.util.concurrent.TimeUnit

object WeatherServiceFactory {

    fun makeService(): WeatherService {
        return makeWeatherService(makeOkkHttpClient(OpenWeatherInterceptor()), makeGson())
    }

    private fun makeWeatherService(okHttpClient: OkHttpClient, gson: Gson): WeatherService {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(WeatherService::class.java)
    }

    private fun makeOkkHttpClient(openWeatherInterceptor: OpenWeatherInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(openWeatherInterceptor)
                .build()
    }

    private fun makeGson() = GsonBuilder().create()

}

class OpenWeatherInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request().url().newBuilder()
                .addQueryParameter("APPID", BuildConfig.OPENWEATHERMAP_API_KEY)
                .build()
        return chain.proceed(chain.request().newBuilder().addHeader("Accept", "application/json").url(url).build())
    }

}