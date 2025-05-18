package com.example.myapplication.order.domain.interactor

import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.data.network.ImagesResponse
import com.example.myapplication.order.domain.TimeFormatter
import com.example.myapplication.order.domain.api.CoordinatesRepository
import com.example.myapplication.order.domain.api.MapInteractorInterface
import com.example.myapplication.order.domain.models.Candidate
import com.example.myapplication.order.domain.models.Order
import com.example.myapplication.order.domain.models.OrderForView
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.order.domain.models.Profession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
        return coordinatesRepository.getProfessions().map { result ->
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

    override fun placeOrder(token: String, order: Order): Flow<String?> {
        return coordinatesRepository.placeOrder(token, order)
    }

    override fun getAllOrders(token: String, userId: String): Flow<Pair<List<OrderDto>?, String?>> {
        return coordinatesRepository.getAllOrders(token, userId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Failed -> {
                    Pair(null, result.message)
                }
            }
        }
    }


    override fun deleteOrder(token: String, orderId: String): Flow<Boolean> {
        return coordinatesRepository.deleteOrder(token, orderId)
    }

    override fun getCandidatesById(
        token: String,
        orderId: String
    ): Flow<Pair<List<Candidate>?, String?>> {
        return coordinatesRepository.getCandidatesByOrderId(token, orderId).map {
            result ->
            when(result){
                is Resource.Success ->{
                    val data = result.data
                    for(candidate in data){
                        val time = TimeFormatter.formatResponseTime(candidate.responseTime)
                        candidate.responseTime = time
                    }
                    Pair(result.data, null)
                }

                is Resource.Failed -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun respondToOrder(token: String, orderId: String): Flow<Boolean> {
        return coordinatesRepository.respondToOrder(token,orderId)
    }


}