package com.example.myapplication.order.domain.api

import com.example.myapplication.order.domain.models.Place
import kotlinx.coroutines.flow.Flow


interface MapInteractorInterface {
    fun execute(query: String) : Flow<Pair<MutableList<Place>?, String?>>
}
