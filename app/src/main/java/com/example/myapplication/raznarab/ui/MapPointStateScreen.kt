package com.example.myapplication.raznarab.ui

import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import com.yandex.mapkit.geometry.Point

sealed interface MapPointStateScreen {
    data class CoordinatesContent(val data : List<Point>) : MapPointStateScreen
    data class ErrorData(val message: String): MapPointStateScreen
}
