package com.example.clothesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.Clothes
import com.example.clothesapp.model.ClothesItem
import com.example.clothesapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {
    private var clothesLiveData = MutableLiveData<List<ClothesItem>>()

    fun getClothes() {
         RetrofitClient.api.getClothes().enqueue(object : Callback<Clothes> {
             override fun onResponse(call: Call<Clothes>, response: Response<Clothes>) {
                 response.body()?.let { clothes ->
                        clothesLiveData.postValue(clothes)
                 }
             }

             override fun onFailure(call: Call<Clothes>, t: Throwable) {
                 Log.e("logg", t.message.toString())
             }
         })
    }

    fun observerClothesLiveData(): LiveData<List<ClothesItem>> {
        return clothesLiveData
    }
}