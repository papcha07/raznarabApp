package com.example.myapplication.authorization.domain.model

sealed interface Resource<T>{
    data class Success<T>(val id: String): Resource<T>
    data class Failed<T>(val message: String ): Resource<T>
}