package com.example.myapplication.order.domain.interactor

import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.domain.api.CoordinatesRepository
import com.example.myapplication.order.domain.api.MapInteractorInterface
import com.example.myapplication.order.domain.models.Order
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.order.domain.models.Profession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapInteractor(private val coordinatesRepository: CoordinatesRepository) :
    MapInteractorInterface {

    override fun execute(query: String): Flow<Pair<MutableList<Place>?, String?>> {
        return coordinatesRepository.searchAddress(query).map { result ->
            when (result) {
                is Resource.Failed -> {
                    Pair(null, result.message)
                }
                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }

    override fun getProfessions(): Flow<Pair<MutableList<Profession>?, String?>> {
        return coordinatesRepository.getProfessions().map {
            result ->
            when(result){
                is Resource.Failed -> {
                    Pair(null, result.message)
                }
                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }

    override fun placeOrder(token: String,order: Order): Flow<String?> {
        return coordinatesRepository.placeOrder(token,order)
    }


}