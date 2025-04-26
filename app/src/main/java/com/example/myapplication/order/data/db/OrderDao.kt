package com.example.myapplication.order.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDao {

    @Query("SELECT * FROM order_table")
    suspend fun getAllOrders() : List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(orderEntity: OrderEntity)

    @Delete
    suspend fun deleteOrder(orderEntity: OrderEntity)

    @Query("DELETE FROM order_table")
    suspend fun deleteAll()
}
