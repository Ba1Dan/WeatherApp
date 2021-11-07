package com.baiganov.weatherapp.data

import com.baiganov.weatherapp.data.network.WeatherApi
import com.baiganov.weatherapp.model.Result
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val weatherApi: WeatherApi
) {

    suspend fun getWeatherForecast(latitude: Double, longitude: Double): Response<Result> {
        return weatherApi.getWeatherForecast(lat = latitude, lon = longitude)
    }

}