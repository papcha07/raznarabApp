package com.example.myapplication.order.data.network

import android.content.Context
import android.util.Log
import com.example.myapplication.order.data.FileConverter
import com.example.myapplication.order.data.dto.geo.GeoCodeRequest
import com.example.myapplication.order.data.dto.Response
import com.example.myapplication.order.data.dto.order.OrderDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class RetrofitNetworkClient(val client: RetrofitClient, val context: Context) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return try {
            if (dto is GeoCodeRequest) {
                withContext(Dispatchers.IO) {
                    Log.d("RetrofitClient", "Отправка запроса: ${dto.address}")
                    val response = client.api.getAddressList(
                        apiKey = "8a570e9a-f3a6-40a3-b6e8-b428579c9127",
                        geocode = dto.address,
                        format = "json"
                    )
                    response.apply {
                        resultCode = 200
                    }
                }
            } else {
                Response().apply {
                    resultCode = 400
                }
            }
        } catch (e: Exception) {
            Response().apply {
                resultCode = -1
            }
        }
    }

    override suspend fun professionRequest(): Response {
        return try {
            withContext(Dispatchers.IO) {
                val response = client.professionApi.getProfessions()
                response.apply {
                    resultCode = 200
                }
            }
        } catch (e: HttpException) {
            Log.d("ProfException", e.message.toString())
            Response().apply {
                resultCode = e.code()
            }
        }
    }

    override suspend fun placeOrderRequest(dto: Any): Response {
        return try {
            if (dto is OrderDto) {
                val imagesPart = dto.imagesFiles.map { uri ->
                    FileConverter.prepareFilePart(context, "imagesFiles", uri)
                }
                withContext(Dispatchers.IO) {
                    val response = client.orderApi.createOrder(
                        dto.description.toRequestBody("text/plain".toMediaType()),
                        dto.lat.toString().toRequestBody("text/plain".toMediaType()),
                        dto.lon.toString().toRequestBody("text/plain".toMediaType()),
                        dto.address.toRequestBody("text/plain".toMediaType()),
                        dto.price.toString().toRequestBody("text/plain".toMediaType()),
                        imagesPart,
                        dto.professionId.toRequestBody("text/plain".toMediaType())
                    )
                    response.apply {
                        resultCode = 200
                    }
                }
            } else {
                Response().apply {
                    resultCode = 401
                }
            }
        } catch (e: HttpException) {
            Response().apply {
                resultCode = e.code()
            }
        }
    }


}