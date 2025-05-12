package com.example.myapplication.raznarab.ui.data.network

import com.example.myapplication.order.data.dto.Response
import com.example.myapplication.order.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MapNetworkClient(val client: RetrofitClient) : MapNetworkClientInterface {

    override suspend fun getAllOrders(token: String): Response {
        return try {
            withContext(Dispatchers.IO){
                val response = client.raznarabApi.getCoordinates(token)
                response.resultCode = 200
                response
            }
        }

        catch (e: HttpException){
            Response().apply {
                resultCode = e.code()
            }
        }
    }

}