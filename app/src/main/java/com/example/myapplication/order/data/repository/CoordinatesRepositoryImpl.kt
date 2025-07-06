package com.example.myapplication.order.data.repository

import android.util.Log
import com.example.myapplication.order.data.dto.geo.GeoCodeRequest
import com.example.myapplication.order.data.dto.geo.GeocodeResponse
import com.example.myapplication.order.data.dto.order.CandidatesResponse
import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.data.dto.order.OrderResponse
import com.example.myapplication.order.data.dto.order.OrdersResponse
import com.example.myapplication.order.data.dto.prof.ProfessionResponse
import com.example.myapplication.order.data.network.NetworkClient
import com.example.myapplication.order.data.network.RetrofitClient
import com.example.myapplication.order.domain.api.CoordinatesRepository
import com.example.myapplication.order.domain.models.Candidate
import com.example.myapplication.order.domain.models.Order
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.order.domain.models.Profession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CoordinatesRepositoryImpl(val networkClient: NetworkClient) : CoordinatesRepository {

    override fun searchAddress(address: String): Flow<Resource<MutableList<Place>>> = flow {
        val response = networkClient.doRequest(GeoCodeRequest(address))
        Log.d("response", response.resultCode.toString())
        when (response.resultCode) {
            200 -> {
                val geoObject =
                    (response as GeocodeResponse).response.geoObjectCollection.featureMember
                if (geoObject.isEmpty()) {
                    emit(Resource.Failed("Ничего не найдено"))
                    Log.d("coordinate", "пусто")
                }
                else {
                    val places = mutableListOf<Place>()
                    for (obj in geoObject) {
                        val address = obj.geoObject.metaDataProperty.geocoderMetaData.text
                        val coordinates = obj.geoObject.point.pos.replace(",", "")  // Удаляем запятую
                        val coordinateParts = coordinates.split(" ")

                        if (coordinateParts.size == 2) {
                            val place = Place(
                                address = address,
                                lat = coordinateParts[1],  // широта
                                lon = coordinateParts[0]   // долгота
                            )
                            places.add(place)
                            Log.d("coordinate", "${coordinateParts[1]}, ${coordinateParts[0]}")
                        } else {
                            Log.d("coordinate", "Неверный формат координат: ${obj.geoObject.point.pos}")
                        }
                        Log.d("coordinate","$coordinates")
                        Log.d("coordinate", "$address")
                        val place = Place(
                            address = address,
                            lat = coordinates.split(" ")[1],
                            lon = coordinates.split(" ")[0]
                        )
                        places.add(place)
                    }
                    emit(Resource.Success(places))
                }
            }
            -1 -> {
                emit(Resource.Failed("Нет интернета"))
                Log.d("coordinate","Ошибка инета")

            }

            else -> {
                emit(Resource.Failed("Ошибка api"))
                Log.d("coordinate","Ошибка инета")
            }
        }
    }

    override fun getProfessions(): Flow<Resource<MutableList<Profession>>> = flow {
        val response = networkClient.professionRequest()
        Log.d("responseProf", response.resultCode.toString())
        when(response.resultCode){

            200 -> {
                val currentProfList = (response as ProfessionResponse).professionsList
                emit(Resource.Success(currentProfList))
            }

            else -> {
                emit(Resource.Failed("Ошибка получения списка профессий"))
            }
        }
    }

    override fun placeOrder(token : String,order: Order): Flow<String?> = flow {
        val response = networkClient.placeOrderRequest(token,order)
        when(response.resultCode){
            200 -> {
                val orderId = (response as OrderResponse).orderId
                emit(orderId)
            }

            else -> {
                emit(null)
            }
        }
    }

    override fun getAllOrders(token: String,userId: String): Flow<Resource<List<OrderDto>>> = flow{
        val response = networkClient.getAllOrdersRequest(token, userId)
        when(response.resultCode){
            200 -> {
                val ordersList = (response as OrdersResponse).orders
                emit(Resource.Success(ordersList))
            }
            else -> {
                emit(Resource.Failed("Невозможно загрузить заказы"))
            }
        }
    }

    override fun getImagesByName(token: String, fileName: String): Flow<ByteArray?> {
        return flow {
            try {
                val response = RetrofitClient.orderApi.getImagesByOrders(token, fileName)
                if (response.isSuccessful) {
                    val imageBytes = response.body()?.bytes()
                    emit(imageBytes)
                } else {
                    emit(null)
                }
            } catch (e: Exception) {
                emit(null)
            }
        }
    }

    override fun deleteOrder(token: String, orderId: String) : Flow<Boolean> = flow {
        val response = networkClient.deleteOrderById(token, orderId)
        when(response.resultCode){
            200 -> emit(true)
            else -> emit(false)
        }
    }

    override fun getCandidatesByOrderId(
        token: String,
        orderId: String
    ): Flow<Resource<List<Candidate>>> = flow {
        val response = networkClient.getCandidatesByOrderId(token, orderId)
        when(response.resultCode){

            200 -> {
                val candidates = (response as CandidatesResponse).candidates
                emit(Resource.Success(candidates))
            }

            -1 -> {
                emit(Resource.Failed("Нет интернета"))
            }

            else -> {
                emit(Resource.Failed("Что-то пошло не так.."))
            }
        }
    }

    override fun respondToOrder(token: String, orderId: String): Flow<Boolean> = flow {
        val response = networkClient.respondToOrder(token, orderId)
        when(response.resultCode){

            200 -> {emit(true)}
            else -> {
                emit(false)
            }
        }
    }

    override fun setExecutor(token: String, orderId: String, executorId: String): Flow<Boolean>  = flow{
        val response = networkClient.setExecutor(
            token,
            orderId,
            executorId
        )

        when(response.resultCode){
            200 -> {
                emit(true)
            }

            else -> {
                emit(false)
            }
        }
    }

    override fun completeOrder(token: String, orderId: String, body: Int): Flow<Boolean>  = flow{
        val response = networkClient.completeOrder(
            token,
            orderId,
            body
        )
        when(response.resultCode){
            200 -> {
                emit(true)
            }
            else -> {
                emit(false)
            }
        }
    }

}