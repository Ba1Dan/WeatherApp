package com.baiganov.weatherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coord(
    @SerializedName("lon") @Expose
    private var lon: Double? = null,
    @SerializedName("lat")
    @Expose
    private val lat: Double? = null
)