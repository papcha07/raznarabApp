package com.example.myapplication.order.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [OrderEntity::class], exportSchema = true)
abstract class OrderDataBase() : RoomDatabase(){
    abstract fun orderDao(): OrderDao
}