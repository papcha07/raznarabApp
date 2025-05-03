package com.example.myapplication.order.ui.placeOrder

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.order.domain.api.MapInteractorInterface
import com.example.myapplication.order.domain.models.Order
import kotlinx.coroutines.launch

class OrderViewModel(private val mapInteractor: MapInteractorInterface) : ViewModel() {

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


    fun placeOrder(order: Order){
       mapInteractor.placeOrder(order)
    }


    init {
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



}