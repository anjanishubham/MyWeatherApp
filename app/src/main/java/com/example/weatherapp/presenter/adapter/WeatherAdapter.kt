package com.example.weatherapp.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherapp.databinding.LayoutWeatherItemBinding
import com.example.weatherapp.domin.Weather

class WeatherAdapter: RecyclerView.Adapter<ViewHolder>() {
    private var weatherList = ArrayList<Weather>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutWeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return WeatherItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ((holder) as WeatherItemViewHolder).onBind(weatherList[position])
    }
    fun setData(arrayList: ArrayList<Weather>){
        weatherList.clear()
        weatherList = arrayList
    }
}