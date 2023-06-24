package com.example.weatherapp.common

object Utils {
    fun convertKelvinToCelsius(temp: Double?): String {
        temp?.let {
            val celsius = (it + Constant.KELVIN_VALUE)
            return String.format("%.2f",celsius)+" C"
        }
        return ""

    }
}