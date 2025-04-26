package com.example.myapplication.order.ui.placeOrder

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.order.domain.api.MapInteractorInterface
import com.example.myapplication.order.domain.api.OrderInteractorInterface
import com.example.myapplication.order.domain.interactor.OrderInteractor
import com.example.myapplication.order.ui.listOrder.Order
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderViewModel(
    private val mapInteractor: MapInteractorInterface,
    private val orderInteractor: OrderInteractorInterface
) : ViewModel() {




    private val photoState = MutableLiveData<MutableList<Uri>>()
    fun getPhotoState() : LiveData<MutableList<Uri>>{
        return photoState
    }

    private val addressState = MutableLiveData<AddressState>()
    fun getAddressState() : LiveData<AddressState> {
        return addressState
    }

    private val profState = MutableLiveData<ProfessionState>()
    fun getProfState() : LiveData<ProfessionState> = profState




    private val stateAddressClick = MutableLiveData<Boolean>()

    fun getAddressClickState() : LiveData<Boolean>{
        return stateAddressClick
    }


    private val ordersState = MutableLiveData<OrdersListState>()
    fun getOrdersState() : LiveData<OrdersListState> = ordersState


     fun addOrder(order: Order){
        viewModelScope.launch {
            orderInteractor.addOrder(order)
            getAllOrders()
        }
    }

     fun deleteOrder(order: Order){
        viewModelScope.launch {
            orderInteractor.deleteOrder(order)
        }
    }

    private fun getAllOrders(){
        viewModelScope.launch {
            val list = orderInteractor.getAllOrders().first()
            when{
                list.size == 0 -> ordersState.postValue(OrdersListState.EmptyList)
                else -> {
                    ordersState.postValue(OrdersListState.Orders(list))
                }
            }
        }
    }


    init {
        getAllOrders()
        loadProfessionList()
    }

    fun loadAddressList(address: String){
        viewModelScope.launch {
            mapInteractor.execute(address).collect{
                pair ->
                val addressList = pair.first
                val errorMessage = pair.second
                if(addressList.isNullOrEmpty()){
                    addressState.postValue(AddressState.Error)
                }
                else{
                    addressState.postValue(AddressState.Content(addressList))
                }
            }
        }
    }

    fun loadProfessionList(){
        viewModelScope.launch {
            mapInteractor.getProfessions().collect{
                pair ->
                val content = pair.first
                val message = pair.second
                if(content == null){
                    profState.postValue(ProfessionState.Error)
                } else{
                    profState.postValue(ProfessionState.Content(content))
                }
            }
        }
    }

    fun chooseAddress() {
        stateAddressClick.postValue(true)
    }

    fun notChooseAddress(){
        stateAddressClick.postValue(false)
    }

    fun setPhotoState(items: MutableList<Uri>){
        photoState.postValue(items)
    }

    fun deleteByUri(deleteUri: Uri) {
        val currentList = photoState.value?.toMutableList() ?: return
        currentList.removeAll { it == deleteUri }
        photoState.value = currentList
        Log.d("deleteByUri", "вызов после ${photoState.value?.size}")
    }

    private fun deletedb(){
        viewModelScope.launch {
            orderInteractor.deleteAll()
        }
    }

}