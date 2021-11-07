package com.baiganov.weatherapp.data.network

import com.baiganov.weatherapp.di.NetworkModule.API_KEY
import com.baiganov.weatherapp.di.NetworkModule.EXCLUDE
import com.baiganov.weatherapp.di.NetworkModule.LAN
import com.baiganov.weatherapp.di.NetworkModule.QUERY_API_KEY
import com.baiganov.weatherapp.di.NetworkModule.QUERY_EXCLUDE
import com.baiganov.weatherapp.di.NetworkModule.QUERY_LANG
import com.baiganov.weatherapp.di.NetworkModule.QUERY_LAT
import com.baiganov.weatherapp.di.NetworkModule.QUERY_LON
import com.baiganov.weatherapp.di.NetworkModule.QUERY_UNITS
import com.baiganov.weatherapp.di.NetworkModule.UNITS
import com.baiganov.weatherapp.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall")
    suspend fun getWeatherForecast(
        @Query(QUERY_LAT)  lat: Double,
        @Query(QUERY_LON)  lon: Double,
        @Query(QUERY_EXCLUDE)  numDays: String = EXCLUDE,
        @Query(QUERY_UNITS) units: String = UNITS,
        @Query(QUERY_LANG) lang: String = LAN,
        @Query(QUERY_API_KEY) apiKey: String = API_KEY
    ): Response<Result>
}