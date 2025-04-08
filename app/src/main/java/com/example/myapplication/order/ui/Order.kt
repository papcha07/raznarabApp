package com.example.myapplication.order.ui

data class Order(
    val id: Int,
    val address: String,
    val category: String,
    val price: String,
    val lon: Double,
    val lat: Double,
    val created : String,
    val title: String
)