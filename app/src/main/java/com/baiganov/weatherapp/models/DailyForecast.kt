package com.baiganov.weatherapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DailyForecast(

    @SerializedName("dt") @Expose
    val dt: Long,
    @SerializedName("temp")
    @Expose
    val temp:  @RawValue Temp,

    @SerializedName("feels_like")
    @Expose
    val feels_like:  @RawValue FeelsLike,

    @SerializedName("pressure")
    @Expose
    val pressure: Double,

    @SerializedName("humidity")
    @Expose
    val humidity: Int,
    @SerializedName("dew_point")
    @Expose
    val dew_point: Double? = null,
    @SerializedName("wind_speed")
    @Expose
    val wind_speed: Double,
    @SerializedName("wind_deg")
    @Expose
    val wind_deg: Int? = null,
    @SerializedName("wind_gust")
    @Expose
    val wind_gust: Double? = null,

    @SerializedName("weather")
    @Expose
    val weather:  @RawValue List<Weather>,

    @SerializedName("speed")
    @Expose
    val speed: Double? = null,

    @SerializedName("deg")
    @Expose
    val deg: Int? = null,

    @SerializedName("clouds")
    @Expose
    val clouds: Int? = null,

    @SerializedName("pop")
    @Expose
    val pop: Double? = null,
    @SerializedName("rain")
    @Expose
    val rain: Double? = null,
    @SerializedName("uvi")
    @Expose
    val uvi: Double? = null,
) : Parcelable