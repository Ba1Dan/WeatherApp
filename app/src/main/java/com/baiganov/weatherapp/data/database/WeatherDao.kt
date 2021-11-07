package com.baiganov.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert
    suspend fun insert(data: List<DailyForecastEntity>)

    @Query("DELETE FROM daily_forecast")
    suspend fun deleteAll()

    @Query("SELECT * FROM daily_forecast")
    suspend fun getWeather(): List<DailyForecastEntity>
}