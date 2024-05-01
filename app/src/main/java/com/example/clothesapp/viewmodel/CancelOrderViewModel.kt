package com.example.clothesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.OrderStatus
import com.example.clothesapp.model.UpdateOrderStatusResponse
import com.example.clothesapp.retrofit.RetrofitClient

class CancelOrderViewModel(): ViewModel() {
    private var updateOrderStatusResponse = MutableLiveData<UpdateOrderStatusResponse>()

    fun cancelOrder(id: String) {
        val orderStatus = OrderStatus(id, "Đã huỷ")
        RetrofitClient.api.updateOrderStatus(orderStatus).enqueue(object : retrofit2.Callback<UpdateOrderStatusResponse> {
            override fun onResponse(call: retrofit2.Call<UpdateOrderStatusResponse>, response: retrofit2.Response<UpdateOrderStatusResponse>) {
                response.body()?.let {
                    updateOrderStatusResponse.value = it
                }
            }

            override fun onFailure(call: retrofit2.Call<UpdateOrderStatusResponse>, t: Throwable) {
                updateOrderStatusResponse.value = UpdateOrderStatusResponse(
                    message = t.message.toString(),
                    isOrder = false
                )
            }
        })
    }

    fun observeUpdateOrderStatusResponse(): LiveData<UpdateOrderStatusResponse> {
        return updateOrderStatusResponse
    }

}