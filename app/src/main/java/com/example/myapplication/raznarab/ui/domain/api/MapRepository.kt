package com.example.myapplication.raznarab.ui.domain.api

import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.raznarab.ui.data.dto.OrderInfoResponse
import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import com.example.myapplication.raznarab.ui.domain.dto.MapOrder
import com.example.myapplication.raznarab.ui.domain.dto.MapOrderForView
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun respondToOrder(token: String, orderId: String): Flow<Resource<String>>
    fun getCoordinates(token: String) : Flow<Resource<List<Coordinate>>>
    fun getInfoByCoordinates(token: String, latitude: Double, longitude: Double) : Flow<Resource<List<MapOrder>>>
}
