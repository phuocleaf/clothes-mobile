package com.example.clothesapp.retrofit

import com.example.clothesapp.model.Clothes
import retrofit2.Call
import retrofit2.http.GET

interface ClothesAPI {

    @GET("products/get-products")
    fun getClothes():Call<Clothes>

}