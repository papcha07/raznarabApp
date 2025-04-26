package com.example.myapplication.order.ui.placeOrder

import com.example.myapplication.order.domain.models.Place

sealed interface AddressState {
    data class Content(val data : List<Place>) : AddressState
    object NotFound: AddressState
    object Error: AddressState
}