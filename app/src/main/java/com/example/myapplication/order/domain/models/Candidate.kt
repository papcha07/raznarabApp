package com.example.myapplication.order.domain.models

data class Candidate(
    val id: String,
    var responseTime: String,
    val rating: Double? = null,
    val type: String,
    val imagePath: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val companyName: String? = null,
    val isExecutor: Boolean,
    val phoneNumber: String
)
