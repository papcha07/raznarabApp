package com.example.myapplication.order.data.dto.order

import android.net.Uri
import java.io.File

data class OrderDto(
    val id: String,
    val title: String,
    val price: Double,
    val professionName: String,
    val createdAt: String,
    val mainImagePath: String?,
    val status : Status
)
