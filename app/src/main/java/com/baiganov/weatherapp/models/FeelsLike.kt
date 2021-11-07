package com.baiganov.weatherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FeelsLike(
    @SerializedName("day")
    @Expose
    val day: Double? = null,
    @SerializedName("night")
    @Expose
    val night: Double? = null,

    @SerializedName("eve")
    @Expose
    val eve: Double? = null,

    @SerializedName("morn")
    @Expose
    val morn: Double? = null
)