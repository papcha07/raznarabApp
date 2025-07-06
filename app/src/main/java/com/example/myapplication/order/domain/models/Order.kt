package com.example.myapplication.order.domain.models

import android.net.Uri

data class Order(
    val title: String,
    val description: String,
    val lat: Double,
    val lon: Double,
    val address: String,
    val price: Double,
    val imagesFiles: List<Uri>,
    val professionId: String
)