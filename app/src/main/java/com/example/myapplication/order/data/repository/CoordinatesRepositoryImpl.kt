package com.example.myapplication.order.data.repository

import android.util.Log
import com.example.myapplication.order.data.dto.GeoCodeRequest
import com.example.myapplication.order.data.dto.GeocodeResponse
import com.example.myapplication.order.data.network.NetworkClient
import com.example.myapplication.order.domain.api.CoordinatesRepository
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoordinatesRepositoryImpl(val networkClient: NetworkClient) : CoordinatesRepository {

    override fun searchAddress(address: String): Flow<Resource<MutableList<Place>>> = flow {
        val response = networkClient.doRequest(GeoCodeRequest(address))
        Log.d("response", response.resultCode.toString())
        when (response.resultCode) {
            200 -> {
                val geoObject =
                    (response as GeocodeResponse).response.geoObjectCollection.featureMember
                if (geoObject.isEmpty()) {
                    emit(Resource.Failed("Ничего не найдено"))
                    Log.d("coordinate", "пусто")
                }
                else {
                    val places = mutableListOf<Place>()
                    for (obj in geoObject) {
                        val address = obj.geoObject.metaDataProperty.geocoderMetaData.text
                        val coordinates =
                            obj.geoObject.point.pos.split(" ").reversed().joinToString(", ")
                        Log.d("coordinate","$coordinates")
                        Log.d("coordinate", "$address")
                        val place = Place(
                            address = address,
                            lat = coordinates.split(" ")[1],
                            lon = coordinates.split(" ")[0]
                        )
                        places.add(place)
                    }
                    emit(Resource.Success(places))
                }
            }
            -1 -> {
                emit(Resource.Failed("Нет интернета"))
                Log.d("coordinate","Ошибка инета")

            }

            else -> {
                emit(Resource.Failed("Ошибка api"))
                Log.d("coordinate","Ошибка инета")
            }
        }
    }
}