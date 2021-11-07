package com.baiganov.weatherapp.data.database

import androidx.room.TypeConverter
import com.baiganov.weatherapp.model.FeelsLike
import com.baiganov.weatherapp.model.Temp
import com.baiganov.weatherapp.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun tempToString(temp: Temp): String {
        return gson.toJson(temp)
    }

    @TypeConverter
    fun stringToTemp(data: String): Temp {
        val listType = object : TypeToken<Temp>() {}.type
        return  gson.fromJson(data, listType)
    }

    @TypeConverter
    fun weatherToString(weather: Weather): String {
        return gson.toJson(weather)
    }

    @TypeConverter
    fun stringToWeather(data: String): Weather {
        val listType = object : TypeToken<Weather>() {}.type
        return  gson.fromJson(data, listType)
    }

    @TypeConverter
    fun feelsLikeToString(feelsLike: FeelsLike): String {
        return gson.toJson(feelsLike)
    }

    @TypeConverter
    fun stringTFeelsLike(data: String): FeelsLike {
        val listType = object : TypeToken<FeelsLike>() {}.type
        return  gson.fromJson(data, listType)
    }
}