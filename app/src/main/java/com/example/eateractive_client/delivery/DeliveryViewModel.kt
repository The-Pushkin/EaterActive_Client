package com.example.eateractive_client.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eateractive_client.server.ServerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DeliveryViewModel(
    private val serverApi: ServerApi,
    private val orderId: Int
) : ViewModel() {
    private val _orderStatus: MutableLiveData<String> = MutableLiveData("Creating")
    val orderStatus: LiveData<String> = _orderStatus

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val orderStatus = serverApi.getOrderStatus(orderId)
                _orderStatus.postValue(orderStatus.body()?.status ?: "no_status")
                delay(1000)
            }
        }
    }

    fun confirmDelivery() = viewModelScope.launch(Dispatchers.IO) {
        serverApi.confirmDelivery(orderId)
    }
}