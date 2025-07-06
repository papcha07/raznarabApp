package com.example.myapplication.raznarab.ui.data.dto

import com.example.myapplication.order.data.dto.Response
import com.example.myapplication.raznarab.ui.domain.dto.Coordinate

data class CoordinatesResponse(
    val coordinates: List<Coordinate>
) : Response()
