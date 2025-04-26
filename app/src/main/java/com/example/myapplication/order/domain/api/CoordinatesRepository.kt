package com.example.myapplication.order.domain.api

import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Resource
import com.example.myapplication.order.domain.models.Profession
import kotlinx.coroutines.flow.Flow

interface CoordinatesRepository {
    fun searchAddress(address: String) : Flow<Resource<MutableList<Place>>>
    fun getProfessions() : Flow<Resource<MutableList<Profession>>>
}
