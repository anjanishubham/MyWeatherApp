package com.example.weatherapp.domin


// current temperature, the minimum and maximum temperature for the day, and a brief description of the current weather conditions.
data class Weather(
    val currentTemp: Double?,
    val miniTemp: Double?,
    val maxTemp: Double?,
    val description: String?,
    val  city:String?
)
