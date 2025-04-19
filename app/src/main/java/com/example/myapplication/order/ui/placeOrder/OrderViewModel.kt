package com.example.myapplication.order.ui.placeOrder

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.order.domain.api.MapInteractorInterface
import com.example.myapplication.order.ui.AddressState
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

    private val stateAddressClick = MutableLiveData<Boolean>()

    fun getAddressClickState() : LiveData<Boolean>{
        return stateAddressClick
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

    fun chooseAddress() {
        stateAddressClick.postValue(true)
    }

    fun notChooseAddress(){
        stateAddressClick.postValue(false)
    }

    fun setPhotoState(items: MutableList<Uri>){
        photoState.postValue(items)
    }

}