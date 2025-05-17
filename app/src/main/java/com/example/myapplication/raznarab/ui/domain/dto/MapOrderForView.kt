package com.example.myapplication.raznarab.ui.domain.dto

data class MapOrderForView(
    val id: String,
    val title: String,
    val mainImagePath: ByteArray,
    val shortDescription: String,
    val address: String,
    val price: Double,
    val professionId: String
)