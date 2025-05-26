package com.example.myapplication.order.ui.placeOrder.state

import com.example.myapplication.order.domain.models.Profession

sealed interface ProfessionState {

    data class Content(val content : MutableList<Profession>) : ProfessionState
    object Error : ProfessionState
}
