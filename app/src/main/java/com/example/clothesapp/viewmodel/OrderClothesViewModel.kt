package com.example.clothesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.ReviewOrder
import com.example.clothesapp.model.ReviewOrderClothes
import com.example.clothesapp.model.ReviewOrderClothesItem
import com.example.clothesapp.model.ReviewOrderItem
import com.example.clothesapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderClothesViewModel(): ViewModel(){

    private var reviewOrderClothesLiveData = MutableLiveData<List<ReviewOrderClothesItem>>()

    fun getOrderClothes(id: String){
        RetrofitClient.api.getOrderClothes(id).enqueue(object : Callback<ReviewOrderClothes> {
            override fun onResponse(call: Call<ReviewOrderClothes>, response: Response<ReviewOrderClothes>) {
                response.body()?.let { reviewOrderClothes ->
                    reviewOrderClothesLiveData.postValue(reviewOrderClothes)
                }
            }

            override fun onFailure(call: Call<ReviewOrderClothes>, t: Throwable) {
                Log.e("logg",t.message.toString())
            }
        })
    }

    fun observerReviewOrderClothesLiveData():LiveData<List<ReviewOrderClothesItem>>{
        return reviewOrderClothesLiveData
    }

}