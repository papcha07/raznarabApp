package com.example.myapplication.order.domain.models

data class Place(
    val address: String,
    val lat : String,
    val lon: String
){
    override fun toString(): String {
        return address
    }
}
