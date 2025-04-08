package com.example.myapplication.order.data.network

import android.util.Log
import com.example.myapplication.order.data.dto.GeoCodeRequest
import com.example.myapplication.order.data.dto.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class RetrofitNetworkClient(val client : RetrofitClient) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        return try {
            if(dto is GeoCodeRequest){
                withContext(Dispatchers.IO){
                    Log.d("RetrofitClient", "Отправка запроса: ${dto.address}")
                    val response = RetrofitClient.api.getAddressList(
                        apiKey = "8a570e9a-f3a6-40a3-b6e8-b428579c9127",
                        geocode = dto.address,
                        format = "json"
                    )
                    response.apply {
                        resultCode = 200
                    }
                }
            }
            else{
                Response().apply {
                    resultCode = 400
                }
            }
        }
        catch (e: Exception){
            Response().apply {
                resultCode = -1
            }
        }
    }
}