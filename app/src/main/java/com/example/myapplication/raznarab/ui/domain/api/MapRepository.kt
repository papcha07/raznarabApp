package com.example.myapplication.raznarab.ui.domain.api

import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun respondToOrder(token: String, orderId: String): Flow<Resource<String>>
     fun getCoordinates(token: String) : Flow<Resource<List<Coordinate>>>
}
