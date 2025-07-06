package com.example.myapplication.order.ui.placeOrder

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.order.domain.api.MapInteractorInterface
import com.example.myapplication.order.domain.models.Order
import com.example.myapplication.order.ui.placeOrder.state.AddressState
import com.example.myapplication.order.ui.placeOrder.state.CandidatesState
import com.example.myapplication.order.ui.placeOrder.state.OrdersListState
import com.example.myapplication.order.ui.placeOrder.state.ProfessionState
import com.example.myapplication.token.domain.TokenInteractor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderViewModel(
    private val mapInteractor: MapInteractorInterface,
    private val tokenInteractor: TokenInteractor
) : ViewModel() {

    private val photoState = MutableLiveData<MutableList<Uri>>()
    fun getPhotoState(): LiveData<MutableList<Uri>> {
        return photoState
    }

    private val addressState = MutableLiveData<AddressState>()
    fun getAddressState(): LiveData<AddressState> {
        return addressState
    }

    private val profState = MutableLiveData<ProfessionState>()
    fun getProfState(): LiveData<ProfessionState> = profState


    private val ordersState = MutableLiveData<OrdersListState>()
    fun getOrdersState(): LiveData<OrdersListState> = ordersState


    private val placeOrderState = MutableLiveData<Boolean?>()
    fun getPlaceOrderState(): LiveData<Boolean?> {
        return placeOrderState
    }


    private val cancelOrderState = MutableLiveData<Boolean>()
    fun getCancelOrderState(): LiveData<Boolean> = cancelOrderState

    fun placeOrder(order: Order) {
        val token = tokenInteractor.getToken()
        viewModelScope.launch {
            val currentOrderId = mapInteractor.placeOrder(token!!, order).first()
            when (currentOrderId) {
                null -> {
                    placeOrderState.postValue(false)
                }

                else -> {
                    placeOrderState.postValue(true)
                }
            }
        }
    }


    init {
        loadProfessionList()
        getAllOrders()
    }

    fun loadAddressList(address: String) {
        viewModelScope.launch {
            mapInteractor.execute(address).collect { pair ->
                val addressList = pair.first
                val errorMessage = pair.second
                if (addressList.isNullOrEmpty()) {
                    addressState.postValue(AddressState.Error)
                } else {
                    addressState.postValue(AddressState.Content(addressList))
                }
            }
        }
    }

    fun loadProfessionList() {
        viewModelScope.launch {
            mapInteractor.getProfessions().collect { pair ->
                val content = pair.first
                val message = pair.second
                if (content == null) {
                    profState.postValue(ProfessionState.Error)
                } else {
                    profState.postValue(ProfessionState.Content(content))
                }
            }
        }
    }


    fun setPhotoState(items: MutableList<Uri>) {
        photoState.postValue(items)
    }

    fun deleteByUri(deleteUri: Uri) {
        val currentList = photoState.value?.toMutableList() ?: return
        currentList.removeAll { it == deleteUri }
        photoState.value = currentList
        Log.d("deleteByUri", "вызов после ${photoState.value?.size}")
    }

    fun getAllOrders() {
        val userId = tokenInteractor.getUserId()
        val token = tokenInteractor.getToken()
        ordersState.postValue(OrdersListState.Loading)
        viewModelScope.launch {
            mapInteractor.getAllOrders(token!!, userId!!).collect { pair ->
                val data = pair.first
                val message = pair.second

                when {

                    data != null -> {
                        if (data.size != 0) {
                            ordersState.postValue(OrdersListState.Orders(data))
                        } else {
                            ordersState.postValue(OrdersListState.EmptyList)
                        }
                    }

                    else -> {
                        ordersState.postValue(OrdersListState.Failed)
                    }
                }

            }
        }
    }

    fun clearObserve() {
        placeOrderState.postValue(null)
    }

    fun cancelOrder(orderId: String) {
        val token = tokenInteractor.getToken()!!
        viewModelScope.launch {
            mapInteractor.deleteOrder(token, orderId).collect { state ->
                when (state) {
                    true -> {
                        cancelOrderState.postValue(true)
                        getAllOrders()
                    }

                    false -> {
                        cancelOrderState.postValue(false)
                    }
                }
            }
        }
    }

    private val candidatesState = MutableLiveData<CandidatesState>()
    fun getCandidateState(): LiveData<CandidatesState> = candidatesState

    fun getAllCandidates(orderId: String) {
        val token = tokenInteractor.getToken()!!
        viewModelScope.launch {
            mapInteractor.getCandidatesById(token, orderId).collect { pair ->
                val data = pair.first
                val message = pair.second

                when {
                    data != null -> {
                        candidatesState.postValue(CandidatesState.Content(data))
                    }
                    else -> {
                        if (message == "Нет интернета") {
                            candidatesState.postValue(CandidatesState.NoInternet)
                        } else {
                            candidatesState.postValue(CandidatesState.Failed)
                        }
                    }
                }
            }
        }
    }

    private val orderStatus = MutableLiveData<Boolean?>()
    fun getOrderStatus() : LiveData<Boolean?> = orderStatus

    fun setExecutor(orderId: String, executorId: String){
        val token = tokenInteractor.getToken()!!
        viewModelScope.launch {
            mapInteractor.setExecutor(token, orderId, executorId).collect{
                state ->
                when(state){
                    true ->{
                        orderStatus.postValue(true)
                    }
                    false -> {
                        orderStatus.postValue(false)
                    }
                }
                getAllOrders()
            }
        }
    }

     val currentRating = MutableLiveData<Int>()

    private val ratingStatus = MutableLiveData<Boolean?>()
    fun getRatingStatus() : LiveData<Boolean?> = ratingStatus

    fun setRating(orderId: String, rating: Int){
        val token = tokenInteractor.getToken()!!
        viewModelScope.launch {
            mapInteractor.completeOrder(token, orderId, rating).collect{
                state ->
                when(state){
                    true -> {
                        ratingStatus.postValue(true)
                    }

                    false -> {
                        ratingStatus.postValue(false)
                    }
                }
            }
        }
    }

    fun resetAll(){
        ratingStatus.postValue(null)
        orderStatus.postValue(null)
    }



}