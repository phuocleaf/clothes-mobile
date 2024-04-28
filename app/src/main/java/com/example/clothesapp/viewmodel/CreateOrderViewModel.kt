package com.example.clothesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.CreateOrderData
import com.example.clothesapp.model.CreateOrderResponse
import com.example.clothesapp.retrofit.RetrofitClient

class CreateOrderViewModel(): ViewModel() {
    private var orderData = CreateOrderData(
        userId = "",
        userAddress = "",
        userName = "",
        userPhone = "",
        cartList = emptyList(),
        total = 0,
        userNote = ""
    )

    private var orderDataResponse = MutableLiveData<CreateOrderResponse>()

    fun createOrder(orderData: CreateOrderData) {
        RetrofitClient.api.createOrder(orderData).enqueue(object : retrofit2.Callback<CreateOrderResponse> {
            override fun onResponse(call: retrofit2.Call<CreateOrderResponse>, response: retrofit2.Response<CreateOrderResponse>) {
                response.body()?.let {
                    orderDataResponse.value = it
                }
            }

            override fun onFailure(call: retrofit2.Call<CreateOrderResponse>, t: Throwable) {
                orderDataResponse.value = CreateOrderResponse(
                    isOrder = false,
                    message = t.message.toString()
                )
            }
        })
    }
    fun setOrderData(orderData: CreateOrderData) {
        this.orderData = orderData
    }

    fun observeOrderDataResponse(): LiveData<CreateOrderResponse> {
        return orderDataResponse
    }
}