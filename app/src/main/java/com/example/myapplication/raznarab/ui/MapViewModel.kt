package com.example.myapplication.raznarab.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.order.domain.api.MapInteractorInterface
import com.example.myapplication.order.domain.interactor.MapInteractor
import com.example.myapplication.profile.domain.UserInfoUseCase
import com.example.myapplication.profile.domain.api.UserInfoUseCaseInterface
import com.example.myapplication.raznarab.ui.domain.api.CoordinatesInteractor
import com.example.myapplication.raznarab.ui.domain.dto.Coordinate
import com.example.myapplication.token.domain.TokenInteractor
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MapViewModel(
    private val coordinatesInteractor: CoordinatesInteractor,
    private val tokenInteractor: TokenInteractor,
    private val userInfoUseCase: UserInfoUseCaseInterface,
    private val mapInteractor: MapInteractorInterface
) : ViewModel() {
    private val coordState = MutableLiveData<MapPointStateScreen>()
    fun getCoordState(): LiveData<MapPointStateScreen> {
        return coordState
    }

    private val avatarImageState = MutableLiveData<String?>()
    fun getAvatarState() : LiveData<String?> = avatarImageState


    init {
        getAllCoordinates()
        getAvatarImage()
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
                } else {
                    coordState.postValue(MapPointStateScreen.ErrorData(message!!))
                }
            }
        }
    }


    private val orderInfoState = MutableLiveData<OrderInfoState>()
    fun getOrderInfoState(): LiveData<OrderInfoState> = orderInfoState

    fun getInfoByPoint(latitude: Double, longitude: Double) {
        val token = tokenInteractor.getToken()!!
        viewModelScope.launch {
            val infoList =
                coordinatesInteractor.getOrdersInfoByCoordinates(token, latitude, longitude)
                    .collect { pair ->
                        val data = pair.first
                        val message = pair.second

                        if (data != null) {
                            orderInfoState.postValue(OrderInfoState.Success(data))
                        } else {
                            orderInfoState.postValue(OrderInfoState.Failed(message!!))
                        }
                    }
        }
    }

    fun getAvatarImage(){
        val id = tokenInteractor.getUserId()
        val token = tokenInteractor.getToken()
        viewModelScope.launch {
            userInfoUseCase.getUserInfo(id!!, token!!).collect{
                    pair ->
                val imagePath = pair.first?.avatarPath
                avatarImageState.postValue(imagePath)
            }
        }
    }

    private val respondOrderState = MutableLiveData<Boolean>()
    fun getRespondOrderState () : LiveData<Boolean>{
        return respondOrderState
    }

    fun respondToOrder(orderId: String){
        val token = tokenInteractor.getToken()!!
        viewModelScope.launch {
            mapInteractor.respondToOrder(token, orderId).collect{
                    boolean ->
                when(boolean){
                    true -> {
                        respondOrderState.postValue(true)
                    }
                    false -> respondOrderState.postValue(false)
                }
            }
        }
    }
}