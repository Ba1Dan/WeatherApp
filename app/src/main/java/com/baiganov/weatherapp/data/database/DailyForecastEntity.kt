package com.baiganov.weatherapp.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baiganov.weatherapp.di.DatabaseModule.DAILY_FORECAST
import com.baiganov.weatherapp.model.FeelsLike
import com.baiganov.weatherapp.model.Temp
import com.baiganov.weatherapp.model.Weather
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = DAILY_FORECAST)
@Parcelize
data class DailyForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dt: Long,
    val temp: @RawValue Temp,
    val weather: @RawValue Weather,
    val pressure: Double,
    val humidity: Int,
    val windSpeed: Double,
    val feelsLike:  @RawValue FeelsLike,
    val cityName: String
) : Parcelable