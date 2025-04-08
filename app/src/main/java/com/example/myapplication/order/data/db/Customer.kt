package com.example.myapplication.order.data.db

import androidx.room.PrimaryKey
import io.realm.RealmObject
import org.bson.types.ObjectId

class Customer: RealmObject()  {
    @io.realm.annotations.PrimaryKey var id : ObjectId = ObjectId()
    var order: Order? = null
}