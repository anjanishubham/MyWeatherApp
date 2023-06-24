package com.example.weatherapp.data.model


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double?=null,
    @SerializedName("humidity")
    val humidity: Int?=null,
    @SerializedName("max")
    val max: Double?=null,
    @SerializedName("min")
    val min: Double?=null,
    @SerializedName("pressure")
    val pressure: Int?=null,
    @SerializedName("temp")
    val temp: Double?=null
)