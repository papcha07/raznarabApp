package com.example.myapplication.raznarab.ui.domain.impl

import com.example.myapplication.order.domain.api.CoordinatesRepository
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.raznarab.ui.domain.api.CoordinatesInteractor
import com.example.myapplication.raznarab.ui.domain.api.MapRepository
import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import com.example.myapplication.raznarab.ui.domain.dto.MapOrder
import com.example.myapplication.raznarab.ui.domain.dto.MapOrderForView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.CertificatePinner

class CoordinatesInteractorImpl(
    private val mapRepository: MapRepository,
    private val coordinatesRepository: CoordinatesRepository
) : CoordinatesInteractor {
    override fun getAllOrders(token: String): Flow<Pair<List<Coordinate>?, String?>> {
        return mapRepository.getCoordinates(token).map {
            result ->
            when(result){
                is Resource.Success -> {
                    val coordList = result.data
                    Pair(coordList, null)
                }
                is Resource.Failed -> {
                    val message = result.message
                    Pair(null, message)
                }
            }
        }
    }

    override fun getOrdersInfoByCoordinates(
        token: String,
        latitude: Double,
        longitude: Double
    ): Flow<Pair<List<MapOrder>?, String?>> {
        return mapRepository.getInfoByCoordinates(token, latitude, longitude).map {
            resource ->
            when(resource){
                is Resource.Success -> {
                    val orders = resource.data
                    Pair(orders, null)
                }

                is Resource.Failed -> {
                    Pair(null, resource.message)
                }
            }
        }
    }

    private fun combineOrderInfoWithImage(token: String, orders: List<MapOrder>) : Flow<List<MapOrderForView>>{
        return flow {
            val ordersWithImages = mutableListOf<MapOrderForView>()
            for(order in orders){
                val imageFlow = coordinatesRepository.getImagesByName(token, order.mainImagePath)
                imageFlow.collect {
                    image ->
                    if(image != null){
                        val mapOrder = MapOrderForView(
                            id = order.id,
                            mainImagePath = image as ByteArray,
                            shortDescription = order.shortDescription,
                            address = order.address,
                            title = order.title,
                            price = order.price,
                            professionId = order.professionId
                        )
                        ordersWithImages.add(mapOrder)
                    }
                }
            }
            emit(ordersWithImages)
        }
    }
}