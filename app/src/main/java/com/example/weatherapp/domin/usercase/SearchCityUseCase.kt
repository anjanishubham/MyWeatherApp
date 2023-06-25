package com.example.weatherapp.domin.usercase

import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.model.WeatherDto
import com.example.weatherapp.data.model.toWeather
import com.example.weatherapp.domin.Weather
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val databaseReference: DatabaseReference
) {
    operator fun invoke(cityName:String): Flow<Resource<List<Weather?>>> {
        return callbackFlow {
            trySend(Resource.Loading())
            val list = ArrayList<Weather>()
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { item ->
                        val obj = item.getValue(WeatherDto::class.java)?.toWeather()
                        obj?.let {
                            list.add(it)
                        }
                    }
                    trySend(Resource.Success(data = list))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(null, error.message))
                }
            }
            val query = databaseReference.orderByChild("name").startAt(cityName).endAt(cityName +"\uf8ff")
            query.addValueEventListener(listener)
            awaitClose { databaseReference.removeEventListener(listener) }
        }
    }
}