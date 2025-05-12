package com.example.myapplication.raznarab.ui.domain.impl

import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.raznarab.ui.domain.api.CoordinatesInteractor
import com.example.myapplication.raznarab.ui.domain.api.MapRepository
import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.CertificatePinner

class CoordinatesInteractorImpl(private val mapRepository: MapRepository) : CoordinatesInteractor {
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
}