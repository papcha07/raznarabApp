package com.example.myapplication.order.domain.models

data class OrderForView(
    val id: String,
    val title: String,
    val price: Double,
    val professionName: String,
    val createdAt: String,
    val mainImagePath: ByteArray,
    val isCancelled: Boolean)