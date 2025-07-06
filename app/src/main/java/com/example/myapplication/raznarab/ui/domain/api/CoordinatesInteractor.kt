package com.example.myapplication.raznarab.ui.domain.api

import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import com.example.myapplication.raznarab.ui.domain.dto.MapOrder
import com.example.myapplication.raznarab.ui.domain.dto.MapOrderForView
import kotlinx.coroutines.flow.Flow

interface CoordinatesInteractor {
    fun getAllOrders(token: String) : Flow<Pair<List<Coordinate>?, String?>>
    fun getOrdersInfoByCoordinates(token: String, latitude: Double, longitude: Double) : Flow<Pair<List<MapOrder>?, String?>>
}