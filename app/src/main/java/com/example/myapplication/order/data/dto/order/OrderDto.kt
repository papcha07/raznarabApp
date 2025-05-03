package com.example.myapplication.order.data.dto.order

import android.net.Uri
import java.io.File

data class OrderDto(
    val description: String,
    val lat: Double,
    val lon: Double,
    val address: String,
    val price: Double,
    val imagesFiles: List<Uri>,
    val professionId: String
)
