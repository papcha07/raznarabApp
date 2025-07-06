package com.example.myapplication.order.data.network

import com.example.myapplication.order.data.dto.Response

data class ImagesResponse(
    val fileBytes: ByteArray
) : Response()
