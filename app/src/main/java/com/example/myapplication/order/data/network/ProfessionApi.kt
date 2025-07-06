package com.example.myapplication.order.data.network

import com.example.myapplication.order.data.dto.prof.ProfessionResponse
import retrofit2.http.GET

interface ProfessionApi {
    @GET("/professions/select-list")
    suspend fun getProfessions(): ProfessionResponse
}
