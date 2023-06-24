package com.example.weatherapp.presenter.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.common.Utils
import com.example.weatherapp.databinding.LayoutWeatherItemBinding
import com.example.weatherapp.domin.Weather

class WeatherItemViewHolder(private val binding: LayoutWeatherItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(weather: Weather) {
        binding.cityView.text =" City : ${weather.city}"
        binding.currentTempView.apply {
            text = "Current Temp is : ${Utils.convertKelvinToCelsius(weather.currentTemp)} "
        }
        binding.minTempView.apply {
            text = "Minimum Temp is : ${Utils.convertKelvinToCelsius(weather.miniTemp)} "

        }
        binding.maxTempView.apply {
            text = "Maximum Temp is : ${Utils.convertKelvinToCelsius(weather.maxTemp)} "

        }
        binding.despView.apply {
            text = "Description : ${weather.description}"
        }
    }
}