package com.example.myapplication.raznarab.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.order.domain.api.MapInteractorInterface
import com.example.myapplication.raznarab.ui.domain.api.CoordinatesInteractor
import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import com.example.myapplication.token.domain.TokenInteractor
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MapViewModel(
    private val coordinatesInteractor: CoordinatesInteractor,
    private val tokenInteractor: TokenInteractor
) : ViewModel() {
    private val coordState = MutableLiveData<MapPointStateScreen>()
    fun getCoordState(): LiveData<MapPointStateScreen> {
        return coordState
    }

    init {
        getAllCoordinates()
    }

    private fun getAllCoordinates() {
        val token = tokenInteractor.getToken()!!
        viewModelScope.launch {
            val coordinateList = coordinatesInteractor.getAllOrders(token).collect { pair ->
                val data = pair.first
                val message = pair.second

                if (data != null) {
                    val newData = data.map {
                        Point(it.latitude, it.longitude)
                    }
                    coordState.postValue(MapPointStateScreen.CoordinatesContent(newData))
                }
                else {
                    coordState.postValue(MapPointStateScreen.ErrorData(message!!))
                }
            }
        }
    }
}