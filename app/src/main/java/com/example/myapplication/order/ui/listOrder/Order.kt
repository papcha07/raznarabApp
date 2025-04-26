package com.example.myapplication.order.ui.listOrder

import android.net.Uri

data class Order(
    val orderId: Int,
    val address: String,
    val category: String,
    val price: Double,
    val description: String,
    val imageUri: String
)