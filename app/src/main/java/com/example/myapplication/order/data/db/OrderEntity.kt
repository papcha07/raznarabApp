package com.example.myapplication.order.data.db

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_table")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int,
    val address: String,
    val category: String,
    val price: Double,
    val description: String,
    val imageUri: String
)