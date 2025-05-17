package com.example.myapplication.raznarab.ui.data

import android.content.Context
import android.util.Log
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import com.example.myapplication.raznarab.ui.data.dto.CoordinatesResponse
import com.example.myapplication.raznarab.ui.data.dto.OrderInfoResponse
import com.example.myapplication.raznarab.ui.data.network.MapNetworkClientInterface
import com.example.myapplication.raznarab.ui.domain.api.MapRepository
import com.example.myapplication.raznarab.ui.domain.dto.MapOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MapRepositoryImpl(private val client: MapNetworkClientInterface) :
    MapRepository {

    override suspend fun respondToOrder(token: String, orderId: String): Flow<Resource<String>> = flow{
        emit(Resource.Success("dasd"))
    }

    override fun getCoordinates(token: String): Flow<Resource<List<Coordinate>>> = flow {
        val response = client.getAllOrders(token)
        Log.d("coordinatesResultCode", response.resultCode.toString())
        when(response.resultCode){
            200 ->{
                val coordinatesList = (response as CoordinatesResponse).coordinates
                emit(Resource.Success(coordinatesList))
            }
            else -> {
                emit(Resource.Failed("Не получилось получить заказы"))
            }
        }
    }

    override fun getInfoByCoordinates(
        token: String,
        latitude: Double,
        longitude: Double
    ): Flow<Resource<List<MapOrder>>>  = flow{
        val response = client.getInfoByOrders(token, latitude, longitude)
        when(response.resultCode){

            200 -> {
                val info = (response as OrderInfoResponse).orders
                emit(Resource.Success(info))
            }

            else -> {
                val message = "Не получилось получить информацию"
                emit(Resource.Failed(message))
            }
        }
    }

}