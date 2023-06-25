package com.example.weatherapp.presenter

import com.example.weatherapp.domin.Weather

data class WeatherStateFlow(
    var isLoading: Boolean = false,
    var error: String? = "",
    var weatherList: List<Weather?>? = null
)
