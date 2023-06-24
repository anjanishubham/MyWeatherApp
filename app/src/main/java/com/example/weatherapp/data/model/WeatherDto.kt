package com.example.weatherapp.data.model


import com.example.weatherapp.domin.Weather
import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("base")
    val base: String?=null,
    @SerializedName("clouds")
    val clouds: Clouds?=null,
    @SerializedName("cod")
    val cod: Int?=null,
    @SerializedName("coord")
    val coord: Coord?=null,
    @SerializedName("dt")
    val dt: Int?=null,
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("main")
    val main: Main?=null,
    @SerializedName("name")
    val name: String?=null,
    @SerializedName("sys")
    val sys: Sys?=null,
    @SerializedName("timezone")
    val timezone: Int?=null,
    @SerializedName("visibility")
    val visibility: Int?=null,
    @SerializedName("weather")
    val weather: List<WeatherDescription>?=null,
    @SerializedName("wind")
    val wind: Wind?=null
)

fun WeatherDto.toWeather(): Weather {
    return Weather(
        currentTemp = main?.temp,
        miniTemp = main?.min,
        maxTemp = main?.max,
        description = weather?.get(0)?.description,
        city = name)
}