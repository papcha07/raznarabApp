package com.example.myapplication.order.domain.models

sealed interface Resource<T>{
    data class Success<T>(val data: T): Resource<T>
    data class Failed<T>(val message: String ): Resource<T>
}