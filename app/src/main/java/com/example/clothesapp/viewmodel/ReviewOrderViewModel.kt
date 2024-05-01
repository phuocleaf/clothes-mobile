package com.example.clothesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.ReviewOrder
import com.example.clothesapp.model.ReviewOrderItem
import com.example.clothesapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewOrderViewModel(): ViewModel() {
    private var reviewOrderLiveData = MutableLiveData<List<ReviewOrderItem>>()

    fun getReviewOrder(id: String, status: String) {
        RetrofitClient.api.getOrderList(id, status).enqueue(object : Callback<ReviewOrder> {
            override fun onResponse(call: Call<ReviewOrder>, response: Response<ReviewOrder>) {
                response.body()?.let { reviewOrder ->
                    reviewOrderLiveData.postValue(reviewOrder)
                }
            }

            override fun onFailure(call: Call<ReviewOrder>, t: Throwable) {
                Log.e("logg", t.message.toString())
            }
        })
    }

    fun observerReviewOrderLiveData(): LiveData<List<ReviewOrderItem>> {
        return reviewOrderLiveData
    }
}