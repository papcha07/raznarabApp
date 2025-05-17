package com.example.myapplication.raznarab.ui.domain.dto

data class MapOrder (
    val id: String,
    val title: String,
    val mainImagePath: String,
    val shortDescription: String,
    val address: String,
    val price: Double,
    val professionId: String
)