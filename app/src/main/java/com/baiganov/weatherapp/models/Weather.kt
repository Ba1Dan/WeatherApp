package com.baiganov.weatherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id") @Expose
    val id: Int,
    @SerializedName("main")
    @Expose
    val main: String? = null,
    @SerializedName("description")
    @Expose
    val description: String? = null,
    @SerializedName("icon")
    @Expose
    val icon: String? = null
)