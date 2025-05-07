package com.example.myapplication.order.domain.interactor

import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.data.network.ImagesResponse
import com.example.myapplication.order.domain.api.CoordinatesRepository
import com.example.myapplication.order.domain.api.MapInteractorInterface
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

    override fun getImagesByName(token: String, fileName: String): Flow<Any?> {
        return coordinatesRepository.getImagesByName(token, fileName)
    }

    override fun getOrdersWithImages(token: String, userId: String): Flow<List<OrderForView>> {
        return coordinatesRepository.getAllOrders(token, userId)
            .flatMapLatest { resource ->
                if (resource is Resource.Success) {
                    val orders = resource.data
                    combineOrdersWithImages(token, orders)
                } else {
                    flowOf(emptyList())
                }
            }
    }

    override fun deleteOrder(token: String, orderId: String): Flow<Boolean> {
        return coordinatesRepository.deleteOrder(token, orderId)
    }

    fun combineOrdersWithImages(token: String, orders: List<OrderDto>): Flow<List<OrderForView>> {
        return flow {
            val orderForListView = mutableListOf<OrderForView>()
            for (order in orders) {
                val imageFlow = coordinatesRepository.getImagesByName(token, order.mainImagePath)
                imageFlow.collect { image ->
                    if (image != null) {
                        val orderForView = OrderForView(
                            id = order.id,
                            title = order.title,
                            price = order.price,
                            professionName = order.professionName,
                            createdAt = order.createdAt,
                            mainImagePath = image as ByteArray,
                            isCancelled = order.isCancelled
                        )
                        orderForListView.add(orderForView)
                    }
                }
            }
            emit(orderForListView)
        }
    }


}