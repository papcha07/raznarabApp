package com.example.myapplication.order.domain.models

data class Profession(
    val id : String,
    val name: String
){
    override fun toString(): String {
        return name
    }
}