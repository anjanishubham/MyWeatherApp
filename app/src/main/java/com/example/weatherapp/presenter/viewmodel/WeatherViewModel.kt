package com.example.weatherapp.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.WeatherDto
import com.example.weatherapp.data.model.toWeather
import com.example.weatherapp.domin.Weather
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val databaseReference: DatabaseReference):ViewModel() {

    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList: LiveData<List<Weather>> = _weatherList

    fun getWeatherList(){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Weather>()
                snapshot.children.forEach { item ->
                   val obj= item.getValue(WeatherDto::class.java)?.toWeather()
                    obj?.let {
                        list.add(it)
                    }
                }
                _weatherList.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun getCity(cityName:String){
        val query = databaseReference.orderByChild("name").startAt(cityName).endAt(cityName +"\uf8ff")
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Weather>()
                if(snapshot.value ==null){
                    _weatherList.postValue(null)
                    return
                }
                snapshot.children.forEach { item ->
                    val obj= item.getValue(WeatherDto::class.java)?.toWeather()
                    obj?.let {
                        list.add(it)
                    }
                }
                _weatherList.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}