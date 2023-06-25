package com.example.weatherapp.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domin.usercase.SearchCityUseCase
import com.example.weatherapp.domin.usercase.WeatherListUseCase
import com.example.weatherapp.presenter.WeatherStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val userCase: WeatherListUseCase,
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {

    private val _weatherListFlow = MutableStateFlow(WeatherStateFlow())
    val weatherListFlow: StateFlow<WeatherStateFlow> = _weatherListFlow

    init {
        getWeatherListFlow()
    }

    private fun getWeatherListFlow() {
        userCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _weatherListFlow.value = WeatherStateFlow(true)
                }

                is Resource.Success -> {
                    _weatherListFlow.value = WeatherStateFlow(weatherList = it.data)
                    Log.d("TAG", "getWeatherListFlow: ${it.data?.size} ")
                }

                is Resource.Error -> {
                    _weatherListFlow.value = WeatherStateFlow(error = it.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun searchCity(cityName: String) {
        searchCityUseCase(cityName).onEach {
            when (it) {
                is Resource.Loading -> {
                    _weatherListFlow.value = WeatherStateFlow(true)
                }

                is Resource.Success -> {
                    _weatherListFlow.value = WeatherStateFlow(weatherList = it.data)
                    Log.d("TAG", "getWeatherListFlow: ${it.data?.size} ")
                }

                is Resource.Error -> {
                    _weatherListFlow.value = WeatherStateFlow(error = it.message)
                }
            }
        }.launchIn(viewModelScope)
    }


}