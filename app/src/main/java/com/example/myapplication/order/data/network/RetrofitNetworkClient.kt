package com.example.myapplication.order.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.myapplication.order.data.FileConverter
import com.example.myapplication.order.data.dto.geo.GeoCodeRequest
import com.example.myapplication.order.data.dto.Response
import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.domain.models.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.File

class RetrofitNetworkClient(val client: RetrofitClient, val context: Context) : NetworkClient {

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

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

    override suspend fun placeOrderRequest(token : String,dto: Any): Response {
        return try {
            Log.d("REQCHECK", "dto is Order: ${dto is Order}, class: ${dto::class.qualifiedName}")
            if (dto is Order) {
                val imagesPart = dto.imagesFiles.map { uri ->
                    FileConverter.prepareFilePart(context, "ImagesFiles", uri)
                }
                withContext(Dispatchers.IO) {
                    val response = client.orderApi.createOrder(
                        "Bearer ${token}",
                        dto.title.toRequestBody("text/plain".toMediaType()),
                        dto.description.toRequestBody("text/plain".toMediaType()),
                        dto.lat.toString().toRequestBody("text/plain".toMediaType()),
                        dto.lon.toString().toRequestBody("text/plain".toMediaType()),
                        dto.address.toRequestBody("text/plain".toMediaType()),
                        dto.price.toString().toRequestBody("text/plain".toMediaType()),
                        imagesPart,
                        dto.professionId.toRequestBody("text/plain".toMediaType())
                    )
                    Log.d("PLACEORDER", "200")
                    response.resultCode = 200
                    response
                }
            } else {
                Log.d("PLACEORDER", "401")
                Response().apply {
                    resultCode = -1
                }

            }
        } catch (e: HttpException) {
            Log.d("PLACEORDER", e.message())
            Response().apply {
                resultCode = e.code()
            }
        }
    }

    override suspend fun getAllOrdersRequest(token : String,userId: String): Response {
        return try {
            withContext(Dispatchers.IO){
                val response = client.orderApi.getAllOrders("Bearer $token",userId)
                response.resultCode = 200
                response
            }
        }

        catch (e: HttpException){
            Response().apply {
                Log.d("responseMessage", e.response().toString())
                resultCode = e.code()
                val message = e.message
            }
        }
    }

    override suspend fun deleteOrderById(token: String, orderId: String): Response {
        return try {
            withContext(Dispatchers.IO){
                val response = client.orderApi.deleteOrderById("Bearer $token", orderId)
                Response().apply {
                    resultCode = if (response.isSuccessful) 200 else response.code()
                }
            }
        }
        catch (e: HttpException){
            Response().apply {
                resultCode = e.code()
            }
        }
    }

    override suspend fun getCandidatesByOrderId(token: String, orderId: String): Response {
        return try {
            withContext(Dispatchers.IO){
                if(isInternetAvailable(context)){
                    val response = client.orderApi.getCandidatesByOrderId("Bearer $token", orderId)
                    response.resultCode = 200
                    response
                }
                else{
                    Response().apply {
                        resultCode = -1
                    }
                }
            }
        }

        catch (e: HttpException){
            Response().apply {
                resultCode = e.code()
            }
        }
    }

    override suspend fun respondToOrder(token: String, orderId: String): Response {
        return try {
            withContext(Dispatchers.IO){
                if(isInternetAvailable(context)){
                    val response = client.orderApi.respondToOrder("Bearer $token", orderId)
                    Response().apply {
                        resultCode = if (response.isSuccessful) 200 else response.code()
                    }
                }
                else{
                    Response().apply {
                        resultCode = -1
                    }
                }
            }
        }
        catch (e: HttpException){
            Response().apply {
                resultCode = e.code()
            }
        }
    }

}