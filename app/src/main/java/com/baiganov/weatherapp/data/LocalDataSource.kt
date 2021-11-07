package com.baiganov.weatherapp.data

import com.baiganov.weatherapp.data.database.DailyForecastEntity
import com.baiganov.weatherapp.data.database.WeatherDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao
) {

    suspend fun insertData(data: List<DailyForecastEntity>) {
        weatherDao.insert(data)
    }

    suspend fun deleteAll() {
        weatherDao.deleteAll()
    }

    suspend fun getWeather(): List<DailyForecastEntity> {
        return weatherDao.getWeather()
    }
}