package com.example.myapplication.raznarab.ui.domain.api

import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import kotlinx.coroutines.flow.Flow

interface CoordinatesInteractor {
    fun getAllOrders(token: String) : Flow<Pair<List<Coordinate>?, String?>>
}