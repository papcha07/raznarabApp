package com.example.myapplication.raznarab.ui

import com.example.myapplication.order.domain.models.OrderForView
import com.example.myapplication.raznarab.ui.domain.dto.MapOrder
import com.example.myapplication.raznarab.ui.domain.dto.MapOrderForView

sealed interface OrderInfoState {
    data class Success(val orderList: List<MapOrder>) : OrderInfoState
    data class Failed(val message: String) : OrderInfoState
}
